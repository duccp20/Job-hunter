package vn.hoidanit.jobhunter.domain.DTO.Request.job;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyUpdateRequest;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE)
@SuperBuilder
public class JobUpdateRequest extends JobCreationRequest{
    Long id;
}
