package ra.security.service.mapper;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import ra.security.model.domain.*;
import ra.security.model.dto.request.ProductRequest;
import ra.security.model.dto.request.ProductUpdateRequest;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IBrandRepository;
import ra.security.repository.ICategoryRepository;
import ra.security.repository.IColorRepository;
import ra.security.repository.IDiscountRepsository;
import ra.security.service.IGenericMapper;
import ra.security.service.upload_aws.StorageService;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductMapper implements IGenericMapper<Product, ProductRequest, ProductResponse> {
    @Autowired
    private IBrandRepository brandRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IColorRepository colorRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private IDiscountRepsository discountRepsository;

    @Override
    public Product toEntity(ProductRequest productRequest) {

        List<Category> categories = categoryRepository.findAllByIdIn(productRequest.getCategory());
        List<Discount> discounts = discountRepsository.findAllByIdIn(productRequest.getDiscount());

        Optional<Color> color = colorRepository.findById(productRequest.getColors());
        Optional<Brand> b = brandRepository.findById(productRequest.getBrandId());

        List<String> list = new ArrayList<>();
        for (MultipartFile file : productRequest.getFile()) {
            String imgUrl = storageService.uploadFile(file);
            list.add(imgUrl);
        }
        List<ImageProduct> imageProducts = list.stream()
                .map(url -> ImageProduct.builder().image(url).product(new Product()).build())
                .collect(Collectors.toList());

        // Xây dựng đối tượng Products bằng cách sử dụng thông tin từ ProductRequest và các đối tượng đã lấy
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .brand(b.orElse(null))  // Sử dụng orElse để tránh lỗi nếu không tìm thấy thương hiệu
                .category(categories)
                .stock(productRequest.getStock())
                .images(imageProducts)
                .discount(discounts)
                .colors(color.orElse(null))
                .created_at(new Date())
                .status(true)
                .build();

        return product;
    }

    public Product toEntity(ProductUpdateRequest productRequest) {

        List<Category> categories = categoryRepository.findAllByIdIn(productRequest.getCategory());

        Optional<Color> color = colorRepository.findById(productRequest.getColors());

        Optional<Brand> b = brandRepository.findById(productRequest.getBrandId());


        // Xây dựng đối tượng Products bằng cách sử dụng thông tin từ ProductRequest và các đối tượng đã lấy
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .brand(b.orElse(null))  // Sử dụng orElse để tránh lỗi nếu không tìm thấy thương hiệu
                .category(categories)
                .stock(productRequest.getStock())
                .colors(color.orElse(null))
                .created_at(new Date())
                .status(true)
                .build();

        return product;
    }

    @Override
    public ProductResponse toResponse(Product products) {
        List<String> categories = products.getCategory().stream()
                .map(Category::getName).collect(Collectors.toList());
        Optional<Color> color = colorRepository.findById(products.getColors().getId());
        Optional<Brand> b = brandRepository.findById(products.getBrand().getId());
//        String brand = String.valueOf(b.get().getName());

//        List<String> color = products.getColors().stream()
//                .map(Color::getName).collect(Collectors.toList());
        List<String> discount = products.getDiscount().stream()
                .map(Discount::getName).collect(Collectors.toList());
//        String c = String.join(", ", color);

        List<String> images = products.getImages().stream()
                .map(ImageProduct::getImage).collect(Collectors.toList());

        return ProductResponse.builder()
                .id(products.getId())
                .name(products.getName())
                .price(products.getPrice())
                .description(products.getDescription())
                .stock(products.getStock())
                .main_image(products.getMain_image())
                .images(images)
                .category(categories)
                .brand(b)
                .colors(color)
                .discount(discount)
                .created_at(products.getCreated_at())
                .status(products.isStatus())
                .build();
    }

}
