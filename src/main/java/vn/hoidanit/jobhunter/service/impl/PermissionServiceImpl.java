package vn.hoidanit.jobhunter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.DTO.Request.permission.PermissionRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.permission.PermissionUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.Meta;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.permission.PermissionResponse;
import vn.hoidanit.jobhunter.domain.entity.Permission;
import vn.hoidanit.jobhunter.domain.entity.Role;
import vn.hoidanit.jobhunter.domain.mapper.PermissionMapper;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.repository.PermissionRepository;
import vn.hoidanit.jobhunter.service.PermissionService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final PermissionMapper permissionMapper;

    @Override
    public PermissionResponse handleCreatePermission(PermissionRequest permissionRequest) {

        Optional<Permission> existedPermission = permissionRepository
                .findPermissionByApiPathAndMethodAndModule(
                        permissionRequest.getApiPath(),
                        permissionRequest.getMethod(),
                        permissionRequest.getModule());

        if (existedPermission.isPresent())
            throw new AppException(ErrorCode.PERMISSION_EXISTED);

        Permission newPermission = permissionMapper.toPermission(permissionRequest);

        return permissionMapper.toPermissionResponse(permissionRepository.save(newPermission));
    }

    @Override
    public PermissionResponse handleUpdatePermission(PermissionUpdateRequest request) {

        Permission permission = permissionRepository.findById(request.getId())
                .orElseThrow(() ->
                        new AppException(ErrorCode.PERMISSION_NOT_EXISTED)
                );

        Optional<Permission> existedPermission = permissionRepository
                .findPermissionByApiPathAndMethodAndModule(
                        request.getApiPath(),
                        request.getMethod(),
                        request.getModule());

        if (existedPermission.isPresent())
            throw new AppException(ErrorCode.PERMISSION_EXISTED);


        permissionMapper.updatePermission(permission, request);


        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @Override
    public PaginationDTO handleGetAllPermissions(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> listPage = permissionRepository.findAll(spec, pageable);

        List<Permission> permissions = listPage.getContent();

        List<PermissionResponse> res = new ArrayList<>();
        for (Permission p : permissions) {
            res.add(permissionMapper.toPermissionResponse(p));
        }

        Meta meta = Meta.builder()
                .current(listPage.getNumber() + 1)
                .pageSize(listPage.getNumberOfElements())
                .totalPages(listPage.getTotalPages())
                .totalItems(listPage.getTotalElements())
                .build();

        return PaginationDTO.builder()
                .meta(meta)
                .result(res)
                .build();
    }

    @Override
    public void handleDeletePermission(long id) {

        Permission permission = permissionRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ROLE_NOT_EXISTED)
        );

        List<Role> roles = permission.getRoles();

        roles.forEach(r -> r.getPermissions().remove(permission));

        permissionRepository.deleteById(id);
    }
}

