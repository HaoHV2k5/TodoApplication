package vn.G3.TodoApplication.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.G3.TodoApplication.dto.request.authentication.AuthenticationRequest;
import vn.G3.TodoApplication.dto.request.user.UserRequest;
import vn.G3.TodoApplication.dto.response.ApiResponse;
import vn.G3.TodoApplication.entity.User;
import vn.G3.TodoApplication.service.UserService;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public ApiResponse<User> createUser(@RequestBody UserRequest request) {
		ApiResponse<User> apiResponse = new ApiResponse<>();
		apiResponse.setMessage("tao user thanh cong");
		apiResponse.setFiel(this.userService.createUserHandel(request));
		return apiResponse;
	}

	@GetMapping("/users")
	public ApiResponse<List<User>> getUser() {
		ApiResponse<List<User>> apiResponse = new ApiResponse<>();
		apiResponse.setMessage("get user successfully");
		apiResponse.setFiel(this.userService.getUser());
		return apiResponse;
	}

}