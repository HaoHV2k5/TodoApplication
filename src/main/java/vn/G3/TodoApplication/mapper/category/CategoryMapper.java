package vn.G3.TodoApplication.mapper.category;

import java.util.List;

import org.mapstruct.Mapper;

import vn.G3.TodoApplication.dto.response.category.CategoryResponse;
import vn.G3.TodoApplication.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toListCategoryResponses(List<Category> cate);
}
