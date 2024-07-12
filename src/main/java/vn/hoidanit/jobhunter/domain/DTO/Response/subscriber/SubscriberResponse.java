package vn.hoidanit.jobhunter.domain.DTO.Response.subscriber;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.hoidanit.jobhunter.domain.DTO.Request.skill.SkillRequest;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriberResponse {

    long id;
    String email;
    String name;
    List<SkillRequest> skills;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;

}
