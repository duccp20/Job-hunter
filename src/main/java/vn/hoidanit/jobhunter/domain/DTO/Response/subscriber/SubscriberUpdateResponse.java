package vn.hoidanit.jobhunter.domain.DTO.Response.subscriber;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.hoidanit.jobhunter.domain.DTO.Response.skill.SkillResponse;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriberUpdateResponse {

    long id;
    List<SkillResponse> skills;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;
}
