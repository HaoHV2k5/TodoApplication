package vn.G3.TodoApplication.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.G3.TodoApplication.dto.request.user.UserRequest;
import vn.G3.TodoApplication.dto.response.user.UserResponse;
import vn.G3.TodoApplication.entity.User;
import vn.G3.TodoApplication.exception.AppException;
import vn.G3.TodoApplication.exception.ErrorCode;
import vn.G3.TodoApplication.mapper.User.UserMapper;
import vn.G3.TodoApplication.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private UserMapper userMapper;

	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;

	}

	public User createUserHandel(UserRequest request) {
		User user = this.userMapper.toUser(request);

		if (this.userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.EXIST_USER);
		}
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		return this.userRepository.save(user);

	}

	public List<User> getUser() {
		List<User> list = this.userRepository.findAll();
		return list;
	}

	public List<UserResponse> findAllUsersAndAdmins() {
		List<UserResponse> list = this.userMapper.toListUserResponses(userRepository.findAll());
		return list;
	}

	public List<UserResponse> findOnlyUsers() {
		List<UserResponse> list = this.userMapper.toListUserResponses(userRepository.findByRole("ROLE_USER"));
		return list;
	}

}
