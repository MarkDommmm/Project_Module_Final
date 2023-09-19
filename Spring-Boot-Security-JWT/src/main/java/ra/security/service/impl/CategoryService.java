package ra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.security.exception.CustomException;
import ra.security.model.domain.Category;
import ra.security.model.dto.request.CategoryRequest;
import ra.security.model.dto.response.CategoryResponse;
import ra.security.repository.ICategoryRepository;
import ra.security.service.IGenericService;
import ra.security.service.mapper.CategoryMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements IGenericService<CategoryResponse, CategoryRequest, Long> {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(c -> categoryMapper.toResponse(c)).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findById(Long aLong) throws CustomException {
        Optional<Category> c = categoryRepository.findById(aLong);
        return c.map(item -> categoryMapper.toResponse(item)).orElseThrow(() ->
                new CustomException("Category not found"));
    }

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) throws CustomException {
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new CustomException("Category  already exists");
        }
        return categoryMapper.toResponse(categoryRepository.save(categoryMapper.toEntity(categoryRequest)));
    }

    @Override
    public CategoryResponse update(CategoryRequest categoryRequest, Long id) {
        Category category = categoryMapper.toEntity(categoryRequest);
        category.setId(id);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse delete(Long aLong) {
        Optional<Category> id = categoryRepository.findById(aLong);
        if (id.isPresent()) {
            categoryRepository.deleteById(aLong);
            return categoryMapper.toResponse(id.get());
        }
        return null;
    }
    public Category findCategoryById(Long id) throws CustomException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElseThrow(() -> new CustomException("not found category"));
    }
}
