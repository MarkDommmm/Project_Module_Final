package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.security.model.domain.ImageProduct;
import ra.security.model.domain.Product;
import ra.security.model.dto.request.ProductRequest;

import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IProductRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.ProductMapper;
import ra.security.service.upload_aws.StorageService;

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

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(p -> productMapper.toResponse(p)).collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(Long aLong) {
        Optional<Product> p = productRepository.findById(aLong);
        if (p.isPresent()) {
            return productMapper.toResponse(p.get());
        }
        return null;
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) {
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

    @Override
    public ProductResponse delete(Long aLong) {
        Optional<Product> p = productRepository.findById(aLong);
        if (p.isPresent()) {
            productRepository.deleteById(aLong);
            return productMapper.toResponse(p.get());
        }
        return null;
    }
}


