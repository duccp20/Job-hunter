package vn.hoidanit.jobhunter.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.role.RoleRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.role.RoleUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobGetResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobUpdateResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.role.RoleResponse;
import vn.hoidanit.jobhunter.domain.entity.Job;
import vn.hoidanit.jobhunter.domain.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);


    @Mapping(target = "users", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateRole(@MappingTarget Role role, RoleUpdateRequest roleUpdateRequest);
}
