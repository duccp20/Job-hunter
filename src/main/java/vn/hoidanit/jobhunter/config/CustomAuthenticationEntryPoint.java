package vn.hoidanit.jobhunter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        this.delegate.commence(request, response, authException);

        response.setContentType("application/json;charset=UTF-8");



        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        //handle nếu không dùng bearer #video_60
        // NULL
        String errorMessage = errorCode.getMessage();
        Throwable cause = authException.getCause();
        if (cause != null) {
            String message = cause.getMessage();
            if (message != null) errorMessage = message;
        }

        ApiResponse<Object> res = ApiResponse.builder()
                .statusCode(errorCode.getCode())
                .message(errorMessage)
                .data(errorCode)
                .build();

        mapper.writeValue(response.getWriter(), res);
    }
}