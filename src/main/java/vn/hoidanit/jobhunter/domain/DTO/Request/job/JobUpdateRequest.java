package vn.hoidanit.jobhunter.domain.DTO.Request.job;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE)
@SuperBuilder
public class JobUpdateRequest extends JobCreationRequest{

    Long id;
}
