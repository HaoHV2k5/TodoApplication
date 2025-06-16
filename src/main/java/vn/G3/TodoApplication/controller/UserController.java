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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

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

	// @GetMapping("/users")
	// public ApiResponse<List<User>> getUser() {
	// ApiResponse<List<User>> apiResponse = new ApiResponse<>();
	// apiResponse.setMessage("get user successfully");
	// apiResponse.setFiel(this.userService.getUser());
	// return apiResponse;
	// }

	@GetMapping("/users")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getAll(Authentication authentication) {

		try {
			boolean isAdmin = authentication.getAuthorities().stream()
					.anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

			if (isAdmin) {
				return ResponseEntity.ok(userService.findAllUsersAndAdmins());
			} else {
				return ResponseEntity.ok(userService.findOnlyUsers());
			}
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username or password incorrect");
		}

	}

}