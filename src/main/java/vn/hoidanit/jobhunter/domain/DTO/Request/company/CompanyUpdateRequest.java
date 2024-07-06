package vn.hoidanit.jobhunter.domain.DTO.Request.company;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUpdateRequest extends CompanyRequest {
    Long id;
}
