package vn.hoidanit.jobhunter.domain.DTO.Request.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.util.Enum.GenderEnum;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest{

    Long id;
    String name;
    String email;
    String password;
    int age;
    @Enumerated(value = EnumType.STRING)
    GenderEnum gender;

    String address;
}
