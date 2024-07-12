package vn.hoidanit.jobhunter.domain.DTO.Response.resume;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class ResumeResponse {

    Long id;
    String email;
    String url;
    String status;
}
