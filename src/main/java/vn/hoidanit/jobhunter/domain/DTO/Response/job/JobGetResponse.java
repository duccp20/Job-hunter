package vn.hoidanit.jobhunter.domain.DTO.Response.job;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Response.company.CompanyResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.skill.SkillResponse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobGetResponse extends JobResponse{

    List<SkillResponse> skills = new ArrayList<>();
    CompanyResponse company;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;
}
