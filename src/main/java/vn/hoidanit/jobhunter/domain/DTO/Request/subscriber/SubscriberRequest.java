package vn.hoidanit.jobhunter.domain.DTO.Request.subscriber;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.hoidanit.jobhunter.domain.DTO.Request.skill.SkillRequest;

import java.util.List;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriberRequest {

    String email;
    String name;
    List<SkillRequest> skills;


}
