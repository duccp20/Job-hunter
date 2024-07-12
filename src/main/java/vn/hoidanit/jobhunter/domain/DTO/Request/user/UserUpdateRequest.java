package vn.hoidanit.jobhunter.domain.DTO.Request.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.role.RoleUpdateRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest extends UserRequest {
    CompanyUpdateRequest company;

    RoleUpdateRequest role;

}
