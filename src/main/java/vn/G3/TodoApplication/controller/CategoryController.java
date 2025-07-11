package vn.G3.TodoApplication.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.G3.TodoApplication.dto.request.category.CreateCategoryRequest;
import vn.G3.TodoApplication.dto.request.category.UpdateCategoryRequest;
import vn.G3.TodoApplication.dto.response.ApiResponse;
import vn.G3.TodoApplication.dto.response.category.CategoryResponse;
import vn.G3.TodoApplication.service.CategoryService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/createCategory")
    public ApiResponse<CategoryResponse> create(@RequestBody CreateCategoryRequest request) {
        CategoryResponse categoryResponse = this.categoryService.createCategory(request);

        return ApiResponse.<CategoryResponse>builder()
                .message("create Category success")
                .fiel(categoryResponse)
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable String id,
            @RequestBody UpdateCategoryRequest request) {
        CategoryResponse categoryResponse = this.categoryService.updateCategory(id, request);
        return ApiResponse.<CategoryResponse>builder()
                .message("update Category success")
                .fiel(categoryResponse)
                .build();
    }

}
