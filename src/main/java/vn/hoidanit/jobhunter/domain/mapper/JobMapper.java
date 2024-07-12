package vn.hoidanit.jobhunter.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobGetResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobUpdateResponse;
import vn.hoidanit.jobhunter.domain.entity.Job;

@Mapper(componentModel = "spring")
public interface JobMapper {


    @Mapping(target = "resumes", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "company", ignore = true)
    Job toJob(JobCreationRequest jobCreationRequest);


    @Mapping(target = "skills", ignore = true)
    JobCreationResponse toJobCreationResponse(Job newJob);


    @Mapping(target = "resumes", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateJob(@MappingTarget Job job, JobUpdateRequest jobUpdateRequest);

    @Mapping(target = "skills", ignore = true)
    JobUpdateResponse toJobUpdateResponse(Job job);

    JobGetResponse toGetJobResponse(Job j);
}
