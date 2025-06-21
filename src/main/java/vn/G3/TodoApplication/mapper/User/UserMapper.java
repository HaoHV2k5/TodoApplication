package vn.G3.TodoApplication.mapper.User;

import java.util.List;

import org.mapstruct.Mapper;

import vn.G3.TodoApplication.dto.request.user.UserRequest;
import vn.G3.TodoApplication.dto.response.user.UserResponse;
import vn.G3.TodoApplication.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserResponse toUserResponse(User user);

	User toUser(UserRequest userRequest);

	List<UserResponse> toListUserResponses(List<User> list);
}
