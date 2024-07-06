package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobGetResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobUpdateResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.entity.Job;

public interface JobService {
    JobCreationResponse handleCreateJob(JobCreationRequest jobCreationRequest);

    JobUpdateResponse handleUpdateJob(JobUpdateRequest jobUpdateRequest);

    PaginationDTO handleGetAllJobs(Specification<Job> spec, Pageable pageable);

    JobGetResponse handleGetJob(Long id);

    void handleDeleteJob(long id);
}
