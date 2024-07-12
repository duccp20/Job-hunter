package vn.hoidanit.jobhunter.domain.DTO.Response.email;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.hoidanit.jobhunter.domain.DTO.Response.skill.SkillResponse;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SendMailSubscriberJob {
    private String name;
    private String location;
    private double salary;
    private List<SkillResponse> skills;
}
