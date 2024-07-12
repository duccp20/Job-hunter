package vn.hoidanit.jobhunter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.DTO.Request.skill.SkillRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.Meta;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.skill.SkillResponse;
import vn.hoidanit.jobhunter.domain.entity.Job;
import vn.hoidanit.jobhunter.domain.entity.Skill;
import vn.hoidanit.jobhunter.domain.entity.Subscriber;
import vn.hoidanit.jobhunter.domain.mapper.SkillMapper;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;
    @Override
    public SkillResponse handleCreateSkill(SkillRequest skillRequest) {

        boolean existsByName = skillRepository.existsByName(skillRequest.getName());

        if(existsByName) {
            throw new AppException(ErrorCode.SKILL_EXISTED);
        }

        Skill skillEntity = skillMapper.toSkill(skillRequest);

        return skillMapper.toSkillResponse(skillRepository.save(skillEntity));
    }

    @Override
    public SkillResponse handleUpdateSkill(SkillRequest skillRequest) {

        Skill currentSkill = skillRepository.findSkillById(skillRequest.getId()).orElseThrow(
                () -> new AppException(ErrorCode.SKILL_NOT_EXISTED)
        );

        if(currentSkill.getName().equals(skillRequest.getName())) {
            throw new AppException(ErrorCode.SKILL_EXISTED);
        }

        skillMapper.updateSkill(currentSkill, skillRequest);

        skillRepository.save(currentSkill);

        return skillMapper.toSkillResponse(currentSkill);
    }

    @Override
    public PaginationDTO handleGetAllSkill(Specification<Skill> spec, Pageable pageable) {

        Page<Skill> skillPage = skillRepository.findAll(spec, pageable);

        List<Skill> skills = skillPage.getContent();

        List<SkillResponse> skillResponses = new ArrayList<>();
        for (Skill s: skills) {
            skillResponses.add(skillMapper.toSkillResponse(s));
        }

        Meta meta = Meta.builder()
                .current(skillPage.getNumber() + 1)
                .pageSize(skillPage.getNumberOfElements())
                .totalPages(skillPage.getTotalPages())
                .totalItems(skillPage.getTotalElements())
                .build();

        return PaginationDTO.builder()
                .meta(meta)
                .result(skillResponses)
                .build();
    }

    @Override
    public void handleDeleteSkill(long id) {

        Skill skill = skillRepository.findSkillById(id).orElseThrow(
                () -> new AppException(ErrorCode.SKILL_NOT_EXISTED)
        );

        List<Job> jobs = skill.getJobs();
        jobs.forEach(j -> j.getSkills().remove(skill));

        List<Subscriber> subscribers = skill.getSubscribers();
        subscribers.forEach(s -> s.getSkills().remove(skill));

        skillRepository.deleteById(id);
    }
}
