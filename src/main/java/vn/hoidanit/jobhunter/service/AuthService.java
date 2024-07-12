package vn.hoidanit.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.DTO.Response.LoginResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.entity.User;
import vn.hoidanit.jobhunter.domain.mapper.RoleMapper;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;
import vn.hoidanit.jobhunter.util.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;


    public ResponseEntity<ApiResponse<LoginResponse>> handleCreateAccessAndRefreshToken(String email) {

        User user = this.userService.handleGetUserByEmail(email);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        //create access token
        String accessToken = this.jwtTokenUtils.createAccessToken(user);

        //create refresh token
        String refreshToken = this.jwtTokenUtils.createRefreshToken(user);
        this.userService.handleUpdateRefreshToken(email, refreshToken);


        LoginResponse.UserLoginResponse userLoginResponse = LoginResponse.UserLoginResponse
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .role(roleMapper.toRoleResponse(user.getRole()))
                .build();
        LoginResponse loginResponse = LoginResponse
                .builder()
                .accessToken(accessToken)
                .user(userLoginResponse)
                .build();

        //  Set refresh token to cookie
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true) //only work with https, but localhost thi k co tac dung
                .path("/") //url set cookie
                .maxAge(this.jwtTokenUtils.getRefreshTokenExpiration())
                // .domain("example.com")  nếu không set, thì chỉ chính là same domain (không tính sub domain)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(ApiResponse.<LoginResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("login Success!")
                        .data(loginResponse)
                        .build());
    }

    public void handleLogout() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.handleGetUserByEmail(email);

        if (user == null)
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        userService.handleUpdateRefreshToken(email, "");

    }

    public LoginResponse.UserLoginResponse handleFetchUserLogin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.handleGetUserByEmail(email);

        return LoginResponse.UserLoginResponse
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .role(roleMapper.toRoleResponse(user.getRole()))
                .build();
    }
}
