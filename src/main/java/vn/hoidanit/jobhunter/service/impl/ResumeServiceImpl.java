package vn.hoidanit.jobhunter.service.impl;

import com.turkraft.springfilter.builder.FilterBuilder;
import com.turkraft.springfilter.converter.FilterSpecification;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import com.turkraft.springfilter.parser.node.FilterNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.config.SecurityUtil;
import vn.hoidanit.jobhunter.domain.DTO.Request.resume.ResumeRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.resume.ResumeUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.Meta;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeGetResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeUpdateResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.skill.SkillResponse;
import vn.hoidanit.jobhunter.domain.entity.*;
import vn.hoidanit.jobhunter.domain.mapper.ResumeMapper;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.ResumeRepository;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.ResumeService;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    private final UserRepository userRepository;

    private final JobRepository jobRepository;
    private final ResumeMapper resumeMapper;

    private final UserService userService;

    private final FilterParser filterParser;

    private final FilterSpecificationConverter filterSpecificationConverter;

    private final FilterBuilder filterBuilder;


    @Override
    public ResumeCreationResponse handleCreateResume(ResumeRequest request) {

        User user = userRepository.findById(request.getUser().getId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Job job = jobRepository.findById(request.getJob().getId()).orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_EXISTED));

        Resume resume = resumeMapper.toResume(request);

        if (!user.getEmail().equals(request.getEmail())) {
            throw new AppException(ErrorCode.INVALID_EMAIL);
        }

        return resumeMapper.toResumeResponse(resumeRepository.save(resume));
    }

    @Override
    public ResumeUpdateResponse handleUpdateResume(ResumeUpdateRequest request) {

        Resume resume = resumeRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.RESUME_NOT_EXISTED));

        resume.setStatus(request.getStatus());

        Resume updatedResume = resumeRepository.save(resume);

        ResumeUpdateResponse resumeResponse = ResumeUpdateResponse.builder().updatedAt(updatedResume.getUpdatedAt()).updatedBy(updatedResume.getUpdatedBy()).build();

        return resumeResponse;
    }

    @Override
    public void handleDeleteResume(long id) {

        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.RESUME_NOT_EXISTED));

        resumeRepository.deleteById(id);
    }

    @Override
    public ResumeGetResponse handleGetResume(long id) {

        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.RESUME_NOT_EXISTED));

        return this.toResumeGetResponse(resume);
    }

    @Override
    public PaginationDTO handleGetAllResumes(Specification<Resume> spec, Pageable pageable) {

        List<Long> arrJobIds = null;
        String email = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        User currentUser = this.userService.handleGetUserByEmail(email);
        if (currentUser != null) {
            Company userCompany = currentUser.getCompany();
            if (userCompany != null) {
                List<Job> companyJobs = userCompany.getJobs();
                if (companyJobs != null && companyJobs.size() > 0) {
                    arrJobIds = companyJobs.stream().map(x -> x.getId())
                            .collect(Collectors.toList());
                }
            }
        }
        //add custom filter by job ID
        Specification<Resume> jobInSpec = filterSpecificationConverter.convert(filterBuilder.field("job")
                .in(filterBuilder.input(arrJobIds)).get());
        //filter by job ID and client filter request.
        Specification<Resume> finalSpec = jobInSpec.and(spec);

        Page<Resume> resumesPage = resumeRepository.findAll(finalSpec, pageable);

        List<Resume> resumes = resumesPage.getContent();

        List<ResumeGetResponse> resumeGetResponses = new ArrayList<>();

        for (Resume r : resumes) {

            ResumeGetResponse res = this.toResumeGetResponse(r);
            resumeGetResponses.add(res);
        }

        Meta meta = Meta.builder().current(resumesPage.getNumber() + 1).pageSize(resumesPage.getNumberOfElements()).totalPages(resumesPage.getTotalPages()).totalItems(resumesPage.getTotalElements()).build();

        return PaginationDTO.builder().meta(meta).result(resumeGetResponses).build();
    }

    @Override
    public PaginationDTO handleGetAllResumesByUser(Pageable pageable) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        FilterNode node = filterParser.parse("email  : '" + email + "'" + "");
        FilterSpecification<Resume> spec = filterSpecificationConverter.convert(node);

        Page<Resume> resumePage = resumeRepository.findAll(spec, pageable);

        List<ResumeGetResponse> res = new ArrayList<>();

        resumePage.forEach(r -> res.add(resumeMapper.toResumeGetResponse(r)));

        Meta meta = Meta.builder().current(resumePage.getNumber() + 1).pageSize(resumePage.getNumberOfElements()).totalPages(resumePage.getTotalPages()).totalItems(resumePage.getTotalElements()).build();

        return PaginationDTO.builder().meta(meta).result(res).build();
    }


    private ResumeGetResponse toResumeGetResponse(Resume r) {

        ResumeGetResponse res = resumeMapper.toResumeGetResponse(r);

        ResumeGetResponse.UserResponse userResponse = ResumeGetResponse.UserResponse.builder().id(r.getUser().getId()).name(r.getUser().getName()).build();

        ResumeGetResponse.JobResponse jobResponse = ResumeGetResponse.JobResponse.builder().id(r.getJob().getId()).name(r.getJob().getName()).build();

        res.setUser(userResponse);
        res.setJob(jobResponse);

        return res;
    }

}
