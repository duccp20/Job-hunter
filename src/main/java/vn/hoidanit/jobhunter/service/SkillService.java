package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.hoidanit.jobhunter.domain.DTO.Request.skill.SkillRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.skill.SkillResponse;
import vn.hoidanit.jobhunter.domain.entity.Skill;

public interface SkillService {
    SkillResponse handleCreateSkill(SkillRequest skillRequest);

    SkillResponse handleUpdateSkill(SkillRequest skillRequest);

    PaginationDTO handleGetAllSkill(Specification<Skill> spec, Pageable pageable);

    void handleDeleteSkill(long id);
}
