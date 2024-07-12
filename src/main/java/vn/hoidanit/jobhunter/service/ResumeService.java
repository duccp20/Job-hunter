package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.hoidanit.jobhunter.domain.DTO.Request.resume.ResumeRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.resume.ResumeUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeGetResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeUpdateResponse;
import vn.hoidanit.jobhunter.domain.entity.Job;
import vn.hoidanit.jobhunter.domain.entity.Resume;

public interface ResumeService {
    ResumeCreationResponse handleCreateResume(ResumeRequest request);

    ResumeUpdateResponse handleUpdateResume(ResumeUpdateRequest request);

    void handleDeleteResume(long id);

    ResumeGetResponse handleGetResume(long id);

    PaginationDTO handleGetAllResumes(Specification<Resume> spec, Pageable pageable);

    PaginationDTO handleGetAllResumesByUser(Pageable pageable);
}
