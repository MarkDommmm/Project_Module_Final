package ra.security.service.mapper;

import org.springframework.stereotype.Component;
import ra.security.model.domain.Category;
import ra.security.model.dto.request.CategoryRequest;
import ra.security.model.dto.response.CategoryResponse;
import ra.security.service.IGenericMapper;
@Component
public class CategoryMapper implements IGenericMapper<Category, CategoryRequest, CategoryResponse> {
    @Override
    public Category toEntity(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .products(categoryRequest.getProducts())
                .build();
    }

    @Override
    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .products(category.getProducts())
                .build();
    }
}
