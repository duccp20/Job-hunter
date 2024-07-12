package vn.hoidanit.jobhunter.util;

import com.nimbusds.jose.util.Base64;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.config.JwtConfiguration;
import vn.hoidanit.jobhunter.domain.DTO.Response.LoginResponse;
import vn.hoidanit.jobhunter.domain.entity.User;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@Getter
public class JwtTokenUtils {

    private final JwtConfiguration jwtConfiguration;

    @Value("${jwt.accessToken-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refreshToken-expiration}")
    private long refreshTokenExpiration;

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    @Value("${jwt.base64-secret}")
    private String jwtKey;

    public JwtTokenUtils(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public String createAccessToken(User user) {
        Instant now = Instant.now();
        Instant validity = now.plus(accessTokenExpiration, ChronoUnit.SECONDS);

        List<String> listPermission = new ArrayList<>();
        listPermission.add("ROLE_USER_CREATE");
        listPermission.add("ROLE_USER_UPDATE");

//        User information in token
        LoginResponse.UserToken userToken = new LoginResponse.UserToken();
        userToken.setId(user.getId());
        userToken.setName(user.getName());
        userToken.setEmail(user.getEmail());
        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(user.getEmail())
                .claim("user", userToken)
                .claim("permission", listPermission)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JwtConfiguration.JWT_ALGORITHM).build();

        return this.jwtConfiguration.jwtEncoder().encode(JwtEncoderParameters.from(jwsHeader,
                claims)).getTokenValue();
    }

    public String createRefreshToken(User user) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);

        //        User information in token
        LoginResponse.UserToken userToken = new LoginResponse.UserToken();
        userToken.setId(user.getId());
        userToken.setName(user.getName());
        userToken.setEmail(user.getEmail());

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(user.getEmail())
                .claim("user", userToken)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JwtConfiguration.JWT_ALGORITHM).build();

        return this.jwtConfiguration.jwtEncoder().encode(JwtEncoderParameters.from(jwsHeader,
                claims)).getTokenValue();
    }

    public Jwt checkValidRefreshToken(String refreshToken) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
                this.jwtConfiguration.getSecretKey()).macAlgorithm(JwtConfiguration.JWT_ALGORITHM).build();
        try {
            return jwtDecoder.decode(refreshToken);
        } catch (Exception e) {
            System.out.println(">>> JWT refresh error: " + e.getMessage());
            throw e;
        }
    }
}
