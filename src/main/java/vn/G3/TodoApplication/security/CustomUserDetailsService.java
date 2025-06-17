package vn.G3.TodoApplication.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import vn.G3.TodoApplication.entity.User;
import vn.G3.TodoApplication.exception.AppException;
import vn.G3.TodoApplication.exception.ErrorCode;
import vn.G3.TodoApplication.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) {
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_INVALID));

                return new org.springframework.security.core.userdetails.User(
                                user.getUsername(),
                                user.getPassword(),
                                List.of(new SimpleGrantedAuthority(user.getRole())));
        }
}
