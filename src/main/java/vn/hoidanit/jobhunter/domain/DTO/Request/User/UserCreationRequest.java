package vn.hoidanit.jobhunter.domain.DTO.Request.User;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyCreationRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest extends UserRequest {
    String confirmPassword;
    CompanyCreationRequest company;
}
