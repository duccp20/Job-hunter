package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.Request.LoginRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.user.UserCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.LoginResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserCreationResponse;
import vn.hoidanit.jobhunter.domain.entity.User;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.service.AuthService;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;
import vn.hoidanit.jobhunter.util.Enum.SuccessCode;
import vn.hoidanit.jobhunter.util.JwtTokenUtils;

@RestController
@Slf4j
@RequestMapping(value = "api/v1/auth")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    private final AuthService authService;


    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,
                          JwtTokenUtils jwtTokenUtils,
                          UserService userService, AuthService authService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserCreationResponse>> handleRegister(@RequestBody UserCreationRequest user) {

        SuccessCode successCode = SuccessCode.CREATED;

        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<UserCreationResponse>builder()
                        .statusCode(successCode.getCode())
                        .message(successCode.getMessage())
                        .data(userService.handleCreateUser(user))
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> handleLogin(@Valid @RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return authService.handleCreateAccessAndRefreshToken(email);
    }

    @GetMapping("/account")
    public ResponseEntity<ApiResponse<LoginResponse>> handleFetchUserLogin() {

        SuccessCode successCode = SuccessCode.GET_SUCCESS;

        LoginResponse res = LoginResponse
                .builder()
                .user(authService.handleFetchUserLogin())
                .build();

        return ResponseEntity.ok().body(
                ApiResponse.<LoginResponse>builder()
                        .statusCode(successCode.getCode())
                        .message(successCode.getMessage())
                        .data(res)
                        .build()
        );
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> handleCreateNewRefreshToken(
            @CookieValue(name = "refresh_token") String refresh_token) {

        Jwt decodedOldRefreshToken = this.jwtTokenUtils.checkValidRefreshToken(refresh_token);

        String email = decodedOldRefreshToken.getSubject();

        User user = userService.handleFindUserByRefreshTokenAndEmail(refresh_token, email);

        if (user == null) {
            throw new AppException(ErrorCode.Token_Not_Valid);
        }

        return this.authService.handleCreateAccessAndRefreshToken(email);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> handleLogout() {

        this.authService.handleLogout();

        ResponseCookie deleteRefreshTokenCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true) //only work with https, but localhost thi k co tac dung
                .path("/") //url set cookie
                .maxAge(0)
                // .domain("example.com")  nếu không set, thì chỉ chính là same domain (không tính sub domain)
                .build();


        return ResponseEntity.status(HttpStatus.OK.value())
                .header(HttpHeaders.SET_COOKIE, deleteRefreshTokenCookie.toString())
                .body(ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Logout Success")
                        .build());
    }
}
