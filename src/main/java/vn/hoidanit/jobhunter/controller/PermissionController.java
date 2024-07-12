package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.Request.permission.PermissionRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.permission.PermissionUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.company.CompanyResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.permission.PermissionResponse;
import vn.hoidanit.jobhunter.domain.entity.Permission;
import vn.hoidanit.jobhunter.domain.entity.Resume;
import vn.hoidanit.jobhunter.service.PermissionService;
import vn.hoidanit.jobhunter.util.Enum.SuccessCode;

@RestController
@RequestMapping("api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @Valid @RequestBody PermissionRequest permissionRequest) {


        SuccessCode successCode = SuccessCode.CREATED;
        return ResponseEntity.status(successCode.getStatusCode()).body(
          ApiResponse.<PermissionResponse>builder()
                  .data(permissionService.handleCreatePermission(permissionRequest))
                  .message(successCode.getMessage())
                  .statusCode(successCode.getCode())
                  .build()
        );

    }

    @PutMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(
            @Valid @RequestBody PermissionUpdateRequest request ) {

        SuccessCode successCode = SuccessCode.UPDATED;
        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<PermissionResponse>builder()
                        .data(permissionService.handleUpdatePermission(request))
                        .message(successCode.getMessage())
                        .statusCode(successCode.getCode())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationDTO>> handleGetAllPermissions(
            @Filter Specification<Permission> spec,
            Pageable pageable
    ) {

        PaginationDTO paginationDTO = permissionService.handleGetAllPermissions(spec, pageable);
        SuccessCode getSuccess = SuccessCode.GET_SUCCESS;
        return ResponseEntity.ok(
                ApiResponse.<PaginationDTO>builder()
                        .statusCode(getSuccess.getCode())
                        .message(getSuccess.getMessage())
                        .data(paginationDTO)
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> handleDeletePermission(
            @PathVariable long id
    ) {
        SuccessCode getSuccess = SuccessCode.DELETED;

        permissionService.handleDeletePermission(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .statusCode(getSuccess.getCode())
                        .message(getSuccess.getMessage())
                        .data(null)
                        .build()
        );

    }
}
