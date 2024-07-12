package vn.hoidanit.jobhunter.domain.DTO.Response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import vn.hoidanit.jobhunter.domain.DTO.Response.role.RoleResponse;
import vn.hoidanit.jobhunter.domain.entity.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    //mapping accessToken to access_token
    @JsonProperty("access_token")
    String accessToken;
    UserLoginResponse user;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserLoginResponse {
        private long id;
        private String email;
        private String name;
        private RoleResponse role;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserToken{
        private long id;
        private String email;
        private String name;
    }
}
