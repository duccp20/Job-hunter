package vn.hoidanit.jobhunter.domain.DTO.Response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    UserLogin user;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserLogin {
        private long id;
        private String email;
        private String name;

    }
}
