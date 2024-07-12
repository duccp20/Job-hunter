package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.hoidanit.jobhunter.domain.DTO.Request.permission.PermissionRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.permission.PermissionUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.permission.PermissionResponse;
import vn.hoidanit.jobhunter.domain.entity.Permission;

public interface PermissionService {
    PermissionResponse handleCreatePermission(PermissionRequest permissionRequest);

    PermissionResponse handleUpdatePermission(PermissionUpdateRequest request);

    PaginationDTO handleGetAllPermissions(Specification<Permission> spec, Pageable pageable);

    void handleDeletePermission(long id);
}
