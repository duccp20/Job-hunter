package vn.hoidanit.jobhunter.domain.DTO.Response.skill;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class SkillResponse {
    Long id;
    String name;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;
}
