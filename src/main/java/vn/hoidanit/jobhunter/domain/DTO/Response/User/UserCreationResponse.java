package vn.hoidanit.jobhunter.domain.DTO.Response.User;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Response.company.CompanyResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.role.RoleResponse;
import vn.hoidanit.jobhunter.domain.entity.Company;
import vn.hoidanit.jobhunter.util.Enum.GenderEnum;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class UserCreationResponse {

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
