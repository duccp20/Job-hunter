package vn.hoidanit.jobhunter.domain.DTO.Request.subscriber;

import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.domain.DTO.Request.skill.SkillRequest;

import java.util.List;

@Setter
@Getter
public class SubscriberUpdateRequest {

    long id;
    List<SkillRequest> skills;
}
