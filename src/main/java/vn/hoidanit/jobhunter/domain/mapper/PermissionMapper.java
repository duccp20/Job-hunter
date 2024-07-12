package vn.hoidanit.jobhunter.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.hoidanit.jobhunter.domain.DTO.Request.permission.PermissionRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.permission.PermissionUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.permission.PermissionResponse;
import vn.hoidanit.jobhunter.domain.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updatePermission(@MappingTarget Permission permission, PermissionUpdateRequest request);
}
