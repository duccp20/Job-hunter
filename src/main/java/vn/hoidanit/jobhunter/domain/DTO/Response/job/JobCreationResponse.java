package vn.hoidanit.jobhunter.domain.DTO.Response.job;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.util.Enum.LevelEnum;

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
public class JobCreationResponse extends JobResponse {

    List<String> skills = new ArrayList<>();
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;


}
