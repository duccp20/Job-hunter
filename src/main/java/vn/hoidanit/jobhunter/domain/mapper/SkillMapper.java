package vn.hoidanit.jobhunter.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.hoidanit.jobhunter.domain.DTO.Request.User.UserUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.skill.SkillRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.skill.SkillResponse;
import vn.hoidanit.jobhunter.domain.entity.Skill;
import vn.hoidanit.jobhunter.domain.entity.User;

@Mapper(componentModel = "spring")
public interface SkillMapper {


    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "jobs", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "company", ignore = true)
    Skill toSkill(SkillRequest skillRequest);

    SkillResponse toSkillResponse(Skill skill);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "jobs", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateSkill(@MappingTarget Skill skill, SkillRequest skillRequest);
}
