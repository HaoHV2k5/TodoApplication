package vn.G3.TodoApplication.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.G3.TodoApplication.dto.request.user.UserRequest;
import vn.G3.TodoApplication.dto.response.ApiResponse;
import vn.G3.TodoApplication.entity.User;
import vn.G3.TodoApplication.exception.ErrorCode;
import vn.G3.TodoApplication.service.UserService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.Authentication;

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

	// @GetMapping("/userss")
	// public ApiResponse<List<User>> getUser() {
	// ApiResponse<List<User>> apiResponse = new ApiResponse<>();
	// apiResponse.setMessage("get user successfully");
	// apiResponse.setFiel(this.userService.getUser());
	// return apiResponse;
	// }

	@GetMapping("/users")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ApiResponse<List<User>> getAll(Authentication authentication) {
		ApiResponse<List<User>> apiResponse = new ApiResponse<>();
		try {
			boolean isAdmin = authentication.getAuthorities().stream()
					.anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

			if (isAdmin) {
				apiResponse.setFiel(userService.findAllUsersAndAdmins());
				return apiResponse;
			} else {
				apiResponse.setFiel(userService.findOnlyUsers());
				return apiResponse;
			}
		} catch (Exception e) {
			apiResponse.setCode(8);
			apiResponse.setMessage(ErrorCode.AUTHORIZED_INVALID + "");
			return apiResponse;
		}

	}

}