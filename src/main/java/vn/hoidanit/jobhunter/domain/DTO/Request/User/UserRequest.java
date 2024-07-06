package vn.hoidanit.jobhunter.domain.DTO.Request.User;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest{

    String name;
    String email;
    String password;
    String phone;

}
