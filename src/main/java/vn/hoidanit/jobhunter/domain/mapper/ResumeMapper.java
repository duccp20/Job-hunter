package vn.hoidanit.jobhunter.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.hoidanit.jobhunter.domain.DTO.Request.resume.ResumeRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeGetResponse;
import vn.hoidanit.jobhunter.domain.entity.Resume;

@Mapper(componentModel = "spring")
public interface ResumeMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Resume toResume(ResumeRequest request);

    ResumeCreationResponse toResumeResponse(Resume resume);

    @Mapping(target = "companyName", source = "job.company.name")
    ResumeGetResponse toResumeGetResponse(Resume resume);
}
