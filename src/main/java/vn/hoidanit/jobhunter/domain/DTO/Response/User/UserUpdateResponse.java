package vn.hoidanit.jobhunter.domain.DTO.Response.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Response.company.CompanyResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserUpdateResponse extends UserResponse{
    long id;
    String name;
    String email;
    String phone;
    CompanyResponse company;
}
