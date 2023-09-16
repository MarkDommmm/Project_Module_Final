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
import ra.security.model.domain.Brand;
import ra.security.model.domain.Category;
import ra.security.model.domain.Color;
import ra.security.model.domain.Products;
import ra.security.model.dto.request.ProductRequest;
import ra.security.model.dto.response.ProductResponse;
import ra.security.repository.IBrandRepository;
import ra.security.repository.ICategoryRepository;
import ra.security.repository.IColorRepository;
import ra.security.service.IGenericMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductMapper implements IGenericMapper<Products, ProductRequest, ProductResponse> {
    @Autowired
    private IBrandRepository brandRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IColorRepository colorRepository;

//    @Value("${application.bucket.name}")
//    private String bucketName;
//
//    @Autowired
//    private AmazonS3 s3Client;

//    public String uploadFile(MultipartFile file) {
//        // Tạo tên tệp duy nhất dựa trên thời gian
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
//        // Lưu trữ tệp lên nơi bạn muốn (ví dụ: Amazon S3)
//        // Đoạn mã này có thể khác nhau tùy theo nơi bạn lưu trữ tệp
//        s3Client.putObject(new PutObjectRequest(bucketName, fileName, convertMultiPartFileToFile(file))
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//
//        // Trả về URL công khai của tệp đã lưu trữ
//        return s3Client.getUrl(bucketName, fileName).toExternalForm();
//    }
//
//    private File convertMultiPartFileToFile(MultipartFile file) {
//        File convertedFile = new File(file.getOriginalFilename());
//        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
//            fos.write(file.getBytes());
//        } catch (IOException e) {
//            log.error("Error converting multipartFile to file", e);
//        }
//        return convertedFile;
//    }



    @Override
    public Products toEntity(ProductRequest productRequest) {
        // Lấy danh sách danh mục từ danh sách ID
        List<Category> categories = categoryRepository.findAllByIdIn(productRequest.getCategory());

        // Lấy danh sách màu sắc từ danh sách ID
        List<Color> colors = colorRepository.findAllByIdIn(productRequest.getColors());

        // Lấy thương hiệu dựa trên ID
        Optional<Brand> b = brandRepository.findById(productRequest.getBrandId());

        // Lưu trữ tệp lên Amazon S3 và lấy URL công khai của tệp
//        String imageUrl = uploadFile(file);

        // Xây dựng đối tượng Products bằng cách sử dụng thông tin từ ProductRequest và các đối tượng đã lấy
        Products product = Products.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .brand(b.orElse(null))  // Sử dụng orElse để tránh lỗi nếu không tìm thấy thương hiệu
                .category(categories)
                .stock(productRequest.getStock())
//                .image(imageUrl) // Gán URL của tệp vào trường image
                .colors(colors)
                .created_at(new Date())
                .status(true)
                .build();

        // Lưu sản phẩm vào cơ sở dữ liệu (nếu bạn muốn)
        // product = productRepository.save(product);

        return product;
    }

    @Override
    public ProductResponse toResponse(Products products) {
        List<String> categories = products.getCategory().stream()
                .map(Category::getName).collect(Collectors.toList());

        Optional<Brand> b = brandRepository.findById(products.getBrand().getId());
//        String brand = String.valueOf(b.get().getName());

        List<String> color = products.getColors().stream()
                .map(Color::getName).collect(Collectors.toList());

//        String c = String.join(", ", color);

        return ProductResponse.builder()
                .id(products.getId())
                .name(products.getName())
                .price(products.getPrice())
                .description(products.getDescription())
                .stock(products.getStock())
                .image(products.getImage())
                .category(categories)
                .brand(b)
                .colors(color)
                .discount_id(products.getDiscount_id())
                .created_at(products.getCreated_at())
                .status(products.isStatus())
                .build();
    }

}
