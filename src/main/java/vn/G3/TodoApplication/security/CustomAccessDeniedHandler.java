package vn.G3.TodoApplication.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.G3.TodoApplication.exception.ErrorCode;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ErrorCode errorCode = ErrorCode.AUTHENTICATION_INVALID;
        String json = String.format("{\"code\": %d},\"message\":\"%s\"",
                errorCode.getCode(), errorCode.getMessage());
        response.getWriter().write(json);

    }

}
