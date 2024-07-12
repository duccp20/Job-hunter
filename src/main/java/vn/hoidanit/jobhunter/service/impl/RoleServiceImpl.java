package vn.hoidanit.jobhunter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.DTO.Request.permission.PermissionCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.role.RoleRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.role.RoleUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobGetResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.Meta;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.role.RoleResponse;
import vn.hoidanit.jobhunter.domain.entity.Job;
import vn.hoidanit.jobhunter.domain.entity.Permission;
import vn.hoidanit.jobhunter.domain.entity.Role;
import vn.hoidanit.jobhunter.domain.mapper.RoleMapper;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.repository.PermissionRepository;
import vn.hoidanit.jobhunter.repository.RoleRepository;
import vn.hoidanit.jobhunter.service.RoleService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse handleCreateRole(RoleRequest request) {

        Role role = roleRepository.findByName(request.getName());

        if (role != null) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        List<PermissionCreationRequest> requestPermissions = request.getPermissions();

        List<Long> IdPermissions = new ArrayList<>();

        requestPermissions.forEach(p -> IdPermissions.add(p.getId()));

        List<Permission> availablePermissions =
                permissionRepository.findByIdIn(IdPermissions);

        Role newRole = roleMapper.toRole(request);
        newRole.setPermissions(availablePermissions);

        return roleMapper.toRoleResponse(roleRepository.save(newRole));
    }

    @Override
    public RoleResponse handleUpdateRole(RoleUpdateRequest roleUpdateRequest) {

        Role role = this.handleGetRoleById(roleUpdateRequest.getId());

        if (role == null)
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);

        boolean existedName = roleRepository.existsByName(roleUpdateRequest.getName());

//        If I want to update permission with old role name, it will not run
//        because name existed in db. So, I comment this logic.
//        if (existedName) {
//            throw new AppException(ErrorCode.ROLE_EXISTED);
//        }

        List<PermissionCreationRequest> requestPermissions = roleUpdateRequest.getPermissions();

        List<Long> IdPermissions = new ArrayList<>();

        requestPermissions.forEach(p -> IdPermissions.add(p.getId()));

        List<Permission> availablePermissions =
                permissionRepository.findByIdIn(IdPermissions);

        roleMapper.updateRole(role, roleUpdateRequest);
        role.setPermissions(availablePermissions);

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public PaginationDTO handleGetAllRoles(Specification<Role> spec, Pageable pageable) {
        Page<Role> rolePage = roleRepository.findAll(spec, pageable);

        List<Role> roles = rolePage.getContent();

        List<RoleResponse> roleResponses = new ArrayList<>();

        for (Role r : roles) {
            roleResponses.add(roleMapper.toRoleResponse(r));
        }

        Meta meta = Meta.builder()
                .current(rolePage.getNumber() + 1)
                .pageSize(rolePage.getNumberOfElements())
                .totalPages(rolePage.getTotalPages())
                .totalItems(rolePage.getTotalElements())
                .build();

        return PaginationDTO.builder()
                .meta(meta)
                .result(roleResponses)
                .build();
    }

    @Override
    public void handleDeleteRole(long id) {

        Role role = this.handleGetRoleById(id);

        if (role == null)
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);

        roleRepository.deleteById(id);
    }

    @Override
    public Role handleGetRoleById(long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public RoleResponse handleGetRole(long id) {

        Role role = this.handleGetRoleById(id);

        if(role == null)
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);

        return roleMapper.toRoleResponse(role);
    }
}
