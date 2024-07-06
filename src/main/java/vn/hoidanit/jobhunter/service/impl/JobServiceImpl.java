package vn.hoidanit.jobhunter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobGetResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobUpdateResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.Meta;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.skill.SkillResponse;
import vn.hoidanit.jobhunter.domain.entity.Job;
import vn.hoidanit.jobhunter.domain.entity.Skill;
import vn.hoidanit.jobhunter.domain.mapper.JobMapper;
import vn.hoidanit.jobhunter.domain.mapper.SkillMapper;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.service.JobService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    private final SkillMapper skillMapper;
    private final SkillRepository skillRepository;

    @Override
    public JobCreationResponse handleCreateJob(JobCreationRequest jobCreationRequest) {

        Optional<Job> job = jobRepository.findByName(jobCreationRequest.getName());

        if (job.isPresent())
            throw new AppException(ErrorCode.JOB_EXISTED);

        Job newJob = jobMapper.toJob(jobCreationRequest);

        List<Long> ListIdSkill = new ArrayList<>();

        for (Skill s : newJob.getSkills()) {
            ListIdSkill.add(s.getId());
        }

        List<Skill> availableSkills = skillRepository.findByIdIn(ListIdSkill);

        newJob.setSkills(availableSkills);

        jobRepository.save(newJob);

        List<String> stringSkill = new ArrayList<>();
        for (Skill s : newJob.getSkills()) {
            stringSkill.add(s.getName());
        }

        JobCreationResponse res = jobMapper.toJobCreationResponse(jobRepository.save(newJob));
        res.setSkills(stringSkill);

        return res;
    }

    @Override
    public JobUpdateResponse handleUpdateJob(JobUpdateRequest jobUpdateRequest) {

        Job job = jobRepository.findById(jobUpdateRequest.getId()).orElseThrow(
                () -> new AppException(ErrorCode.JOB_NOT_EXISTED)
        );

        jobMapper.updateJob(job, jobUpdateRequest);

        List<Skill> skills = job.getSkills();

        List<Long> listIdSkills = new ArrayList<>();
        for (Skill s: skills ) {
            listIdSkills.add(s.getId());
        }

        List<Skill> availableSkills = skillRepository.findByIdIn(listIdSkills);
        job.setSkills(availableSkills);

        JobUpdateResponse jobUpdateResponse = jobMapper.toUpdateJob(jobRepository.save(job));

        List<String> nameSkills = new ArrayList<>();
        for (Skill s: availableSkills) {
            nameSkills.add(s.getName());
        }

        jobUpdateResponse.setSkills(nameSkills);

        return jobUpdateResponse;
    }

    @Override
    public PaginationDTO handleGetAllJobs(Specification<Job> spec, Pageable pageable) {

        Page<Job> jobsPage = jobRepository.findAll(pageable);

        List<Job> jobs = jobsPage.getContent();

        List<JobGetResponse> jobGetResponses = new ArrayList<>();

        for (Job j: jobs) {
            jobGetResponses.add(jobMapper.toGetJobResponse(j));
        }

        Meta meta = Meta.builder()
                .current(jobsPage.getNumber() + 1)
                .pageSize(jobsPage.getNumberOfElements())
                .totalPages(jobsPage.getTotalPages())
                .totalItems(jobsPage.getTotalElements())
                .build();

        return PaginationDTO.builder()
                .meta(meta)
                .result(jobGetResponses)
                .build();
    }

    @Override
    public JobGetResponse handleGetJob(Long id) {

        Job job = jobRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.JOB_NOT_EXISTED)
        );

        return jobMapper.toGetJobResponse(job);
    }

    @Override
    public void handleDeleteJob(long id) {

        Job job = jobRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.JOB_NOT_EXISTED)
        );

        jobRepository.deleteById(job.getId());
    }
}
