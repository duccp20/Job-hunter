package vn.hoidanit.jobhunter.service;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.hoidanit.jobhunter.domain.DTO.Request.role.RoleRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.role.RoleUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.role.RoleResponse;
import vn.hoidanit.jobhunter.domain.entity.Role;

public interface RoleService {


    RoleResponse handleCreateRole(RoleRequest request);

    RoleResponse handleUpdateRole(RoleUpdateRequest roleUpdateRequest);

    PaginationDTO handleGetAllRoles(Specification<Role> spec, Pageable pageable);

    void handleDeleteRole(long id);

    Role handleGetRoleById(long id);

    RoleResponse handleGetRole(long id);
}
