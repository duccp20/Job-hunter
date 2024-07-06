package vn.hoidanit.jobhunter.domain.DTO.Request.company;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyCreationRequest extends CompanyRequest{
    Long id;
}
