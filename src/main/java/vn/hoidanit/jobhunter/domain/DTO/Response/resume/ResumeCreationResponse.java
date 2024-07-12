package vn.hoidanit.jobhunter.domain.DTO.Response.resume;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class ResumeCreationResponse extends ResumeResponse{

    Instant createdAt;
    String createdBy;
}
