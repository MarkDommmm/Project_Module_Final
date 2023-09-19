package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ra.security.exception.CustomException;
import ra.security.model.domain.*;
import ra.security.model.dto.request.ProductRequest;

import ra.security.model.dto.request.ProductUpdateRequest;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IBrandRepository;
import ra.security.repository.IImageProductRepository;
import ra.security.repository.IProductRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.ProductMapper;
import ra.security.service.upload_aws.StorageService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements IGenericService<ProductResponse, ProductRequest, Long> {

    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StorageService storageService;
    @Autowired
    private IImageProductRepository imageProductRepository;
    @Autowired
    private IBrandRepository brandRepository;
    @Autowired
    private ImageProductService imageProductService;

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(p -> productMapper.toResponse(p)).collect(Collectors.toList());
    }
    public List<ProductResponse> findAllInUser() {
        return productRepository.findAll().stream()
                .filter(p -> p.isStatus()) // Lọc các sản phẩm có trạng thái là true
                .map(p -> productMapper.toResponse(p))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(Long aLong) throws CustomException {
        Optional<Product> p = productRepository.findById(aLong);

        if (p.isPresent()) {
            return productMapper.toResponse(p.get());
        }
        throw  new CustomException("Product not found");
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) throws CustomException {
        Product product = productMapper.toEntity(productRequest);
        List<String> listUrl = new ArrayList<>();
        for (MultipartFile m : productRequest.getFile()) {
            listUrl.add(storageService.uploadFile(m));
        }
        // setMain_image() vào cái ảnh đầu tiên
        product.setMain_image(listUrl.get(0));
        List<ImageProduct> imageProducts = new ArrayList<>();
        for (String url : listUrl) {
            imageProducts.add(ImageProduct.builder().image(url).product(product).build());
        }
        product.setImages(imageProducts);
//        Product p = productRepository.save(productMapper.toEntity(productRequest));
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(ProductRequest productRequest, Long id) {
        Product p = productMapper.toEntity(productRequest);
        p.setId(id);
        return productMapper.toResponse(productRepository.save(p));
    }

    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest, Long id) {
        Product p = productMapper.toEntity(productUpdateRequest);
        p.setId(id);
        List<String> categories = p.getCategory().stream()
                .map(Category::getName).collect(Collectors.toList());

        Optional<Brand> b = brandRepository.findById(p.getBrand().getId());
//        String brand = String.valueOf(b.get().getName());

        List<String> color = p.getColors().stream()
                .map(Color::getName).collect(Collectors.toList());
        ProductResponse productResponse = ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .description(p.getDescription())
                .stock(p.getStock())
                .category(categories)
                .brand(b)
                .colors(color)
                .discount_id(p.getDiscount_id())
                .created_at(p.getCreated_at())
                .status(p.isStatus())
                .build();
        productRepository.save(p);
        return productResponse;
    }

    @Override
    public ProductResponse delete(Long aLong) throws CustomException {
        Optional<Product> p = productRepository.findById(aLong);
        if (p.isPresent()) {
            productRepository.deleteById(aLong);
        }else {
            throw  new CustomException("Product not found");
        }
    return null;
    }

    public Product findProductById(Long id) throws CustomException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElseThrow(() -> new CustomException("product not found"));
    }

    public ProductResponse addImageToProduct(MultipartFile multipartFile, Long id) throws CustomException {
        Product product = findProductById(id);
        String url = storageService.uploadFile(multipartFile);
        product.getImages().add(
                ImageProduct.builder()
                        .image(url)
                        .product(product)
                        .build());
        return productMapper.toResponse(productRepository.save(product));
    }

    public ProductResponse changeImageProduct(MultipartFile multipartFile, Long id) throws CustomException {
        Product product = findProductById(id);
        String url = storageService.uploadFile(multipartFile);
        product.setMain_image(url);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse deleteImageInProduct(Long idImage, Long idProduct) throws  CustomException {
        ImageProduct imageProduct = findImageProductById(idImage);
        Product product = findProductById(idProduct);
        product.getImages().remove(imageProduct);
        imageProductService.delete(idImage);
        return productMapper.toResponse(product);
    }

    public ImageProduct findImageProductById(Long idImage) throws CustomException {
        Optional<ImageProduct> optionalImageProduct = imageProductRepository.findById(idImage);
        return optionalImageProduct.orElseThrow(() -> new CustomException("Image not found"));
    }

    public ProductResponse changeStatus(Long id) throws CustomException {
        Product product = findProductById(id);
        product.setStatus(!product.isStatus());
        return productMapper.toResponse(productRepository.save(product));
    }

    public ProductResponse changeBrand(Long idProduct, Long idBrand) throws CustomException {
        Product p = findProductById(idProduct);
        Optional<Brand> brand = brandRepository.findById(idBrand);
        p.setBrand(brand.get());
        return productMapper.toResponse(productRepository.save(p));
    }

}


