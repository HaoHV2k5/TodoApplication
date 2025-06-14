package vn.G3.TodoApplication.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import vn.G3.TodoApplication.dto.request.authentication.AuthenticationRequest;
import vn.G3.TodoApplication.dto.request.token.IntrospectRequest;
import vn.G3.TodoApplication.dto.response.ApiResponse;
import vn.G3.TodoApplication.dto.response.AuthenticationResponse;
import vn.G3.TodoApplication.dto.response.token.IntrospectResponse;
import vn.G3.TodoApplication.service.AuthenticationService;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();

        AuthenticationResponse result = this.authenticationService.checkLogin(request);
        apiResponse.setFiel(result);
        apiResponse.setMessage("login successful");
        return apiResponse;

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
