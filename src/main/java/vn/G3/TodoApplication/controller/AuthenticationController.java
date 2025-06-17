package vn.G3.TodoApplication.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import vn.G3.TodoApplication.dto.request.authentication.AuthenticationRequest;
import vn.G3.TodoApplication.dto.request.token.IntrospectRequest;
import vn.G3.TodoApplication.dto.response.ApiResponse;
import vn.G3.TodoApplication.dto.response.AuthenticationResponse;
import vn.G3.TodoApplication.dto.response.token.IntrospectResponse;
import vn.G3.TodoApplication.exception.AppException;
import vn.G3.TodoApplication.exception.ErrorCode;
import vn.G3.TodoApplication.security.JwtUtils;
import vn.G3.TodoApplication.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import vn.G3.TodoApplication.security.JwtUtils;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()));

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            String token = jwtUtils.generateToken(userDetails);
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAuthented(true);
            authenticationResponse.setToken(token);

            return ApiResponse.<AuthenticationResponse>builder()

                    .fiel(authenticationResponse)
                    .message("success")
                    .code(1000)
                    .build();
        } catch (BadCredentialsException e) {
            throw new AppException(ErrorCode.PASSWORD_INVALID);
        }

    }

    @PostMapping("/introSpect")
    public ApiResponse<IntrospectResponse> introSpect(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<>();

        var result = this.authenticationService.introspet(request);
        apiResponse.setFiel(result);
        return apiResponse;

    }

}
