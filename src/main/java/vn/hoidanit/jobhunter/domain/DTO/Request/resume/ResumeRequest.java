package vn.hoidanit.jobhunter.domain.DTO.Request.resume;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Request.user.UserRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobRequest;
import vn.hoidanit.jobhunter.util.Enum.ResumeStateEnum;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeRequest {

    String email;
    String url;
    @Enumerated(EnumType.STRING)
    ResumeStateEnum status;
    UserRequest user;
    JobRequest job;
}
