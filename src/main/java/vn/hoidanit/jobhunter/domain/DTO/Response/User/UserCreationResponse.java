package vn.hoidanit.jobhunter.domain.DTO.Response.User;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Response.company.CompanyResponse;
import vn.hoidanit.jobhunter.domain.entity.Company;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class UserCreationResponse {

    long id;
    String name;
    String email;
    String phone;
    CompanyResponse company;

}
