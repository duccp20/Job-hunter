package vn.hoidanit.jobhunter.domain.DTO.Response.resume;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeResponse;

import java.time.Instant;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeGetResponse extends ResumeResponse {

    Instant createdAt;
    Instant updatedAt;

    String createdBy;
    String updatedBy;
    String companyName;

    UserResponse user;

    JobResponse job;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserResponse {
        private long id;
        private String name;

    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class JobResponse {
        private long id;
        private String name;

    }
}
