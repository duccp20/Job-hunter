package vn.hoidanit.jobhunter.domain.DTO.Response.User;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Response.company.CompanyResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.role.RoleResponse;
import vn.hoidanit.jobhunter.util.Enum.GenderEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserUpdateResponse extends UserResponse{
    long id;
    String name;
    String email;
    int age;
    @Enumerated(value = EnumType.STRING)
    GenderEnum gender;

    String address;
    CompanyResponse company;

    RoleResponse role;
}
