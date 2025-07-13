package vn.G3.TodoApplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.G3.TodoApplication.dto.request.category.CategoryGetRequest;
import vn.G3.TodoApplication.dto.request.category.CategoryGetTask;
import vn.G3.TodoApplication.dto.request.category.CreateCategoryRequest;
import vn.G3.TodoApplication.dto.request.category.UpdateCategoryRequest;
import vn.G3.TodoApplication.dto.response.category.CategoryResponse;
import vn.G3.TodoApplication.dto.response.task.TaskResponse;
import vn.G3.TodoApplication.entity.Category;
import vn.G3.TodoApplication.entity.Task;
import vn.G3.TodoApplication.exception.AppException;
import vn.G3.TodoApplication.exception.ErrorCode;
import vn.G3.TodoApplication.mapper.category.CategoryMapper;
import vn.G3.TodoApplication.mapper.task.TaskMapper;
import vn.G3.TodoApplication.repository.CategoryRepository;
import vn.G3.TodoApplication.repository.TaskRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    TaskMapper taskMapper;
    TaskRepository taskRepository;

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

    public CategoryResponse updateCategory(String id, UpdateCategoryRequest request) {
        var category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));

        String categoryName = request.getCategoryName().trim();
        category.setCategoryName(categoryName);
        this.categoryRepository.save(category);
        CategoryResponse categoryResponse = this.categoryMapper.toCategoryResponse(category);
        return categoryResponse;

    }

    public CategoryResponse deleteCategory(String id) {
        var category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));
        this.categoryRepository.deleteById(id);
        CategoryResponse categoryResponse = this.categoryMapper.toCategoryResponse(category);
        return categoryResponse;
    }

    public List<CategoryResponse> getCategory() {

        List<Category> list = this.categoryRepository.findAll();
        List<CategoryResponse> result = this.categoryMapper.toListCategoryResponses(list);
        return result;

    }

    public List<TaskResponse> getTaskFollowCategory(CategoryGetTask request) {
        String name = request.getCategoryName();
        List<Task> list = this.taskRepository.findByCategory_CategoryName(name);
        List<TaskResponse> result = this.taskMapper.toListTaskResponses(list);
        return result;

    }

}
