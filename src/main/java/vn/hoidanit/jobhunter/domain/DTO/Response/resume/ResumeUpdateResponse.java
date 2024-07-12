package vn.hoidanit.jobhunter.domain.DTO.Response.resume;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeUpdateResponse {

    Instant updatedAt;
    String updatedBy;
}
