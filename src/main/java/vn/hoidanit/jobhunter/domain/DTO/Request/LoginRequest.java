package vn.hoidanit.jobhunter.domain.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequest {

    @NotBlank(message = "Username cannot be empty!")
    String username;

    @NotBlank(message = "Password cannot be empty!")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;

}
