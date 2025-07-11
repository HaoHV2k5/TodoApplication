package vn.G3.TodoApplication.service;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.G3.TodoApplication.dto.request.category.CreateCategoryRequest;
import vn.G3.TodoApplication.dto.response.category.CategoryResponse;
import vn.G3.TodoApplication.entity.Category;
import vn.G3.TodoApplication.mapper.category.CategoryMapper;
import vn.G3.TodoApplication.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CreateCategoryRequest request) {
        String name = request.getCategoryName();
        // can them xu li loi khi toan dau cach
        Category category = new Category();
        if (!name.trim().isEmpty()) {

            category.setCategoryName(name);
            this.categoryRepository.save(category);

        }
        CategoryResponse categoryResponse = this.categoryMapper
                .toCategoryResponse(category);
        return categoryResponse;
    }
}
