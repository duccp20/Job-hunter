package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.Request.role.RoleRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.role.RoleUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.role.RoleResponse;
import vn.hoidanit.jobhunter.domain.entity.Role;
import vn.hoidanit.jobhunter.service.RoleService;
import vn.hoidanit.jobhunter.util.Enum.SuccessCode;


@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> handleCreateRole(@Valid @RequestBody RoleRequest request) {

        SuccessCode successCode = SuccessCode.CREATED;
        return ResponseEntity.status(successCode.getStatusCode()).body
                (ApiResponse.<RoleResponse>
                                builder()
                        .statusCode(successCode.getStatusCode().value())
                        .message(successCode.getMessage())
                        .data(roleService.handleCreateRole(request))
                        .build());

    }

    @PutMapping
    public ResponseEntity<ApiResponse<RoleResponse>> handleUpdateRole(@Valid @RequestBody RoleUpdateRequest roleUpdateRequest) {

        SuccessCode updatedSuccess = SuccessCode.UPDATED;
        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>
                                builder()
                        .statusCode(updatedSuccess.getCode())
                        .message(updatedSuccess.getMessage())
                        .data(roleService.handleUpdateRole(roleUpdateRequest))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationDTO>> handleGetAllRoles(@Filter Specification<Role> spec, Pageable pageable) {

        PaginationDTO paginationDTO = roleService.handleGetAllRoles(spec, pageable);
        SuccessCode getSuccess = SuccessCode.GET_SUCCESS;
        return ResponseEntity.ok(ApiResponse.<PaginationDTO>builder().statusCode(getSuccess.getCode()).message(getSuccess.getMessage()).data(paginationDTO).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> handleGetRole(@PathVariable long id) {
        SuccessCode getSuccess = SuccessCode.GET_SUCCESS;
        return ResponseEntity.ok(ApiResponse.<RoleResponse>builder().statusCode(getSuccess.getCode()).message(getSuccess.getMessage()).data(roleService.handleGetRole(id)).build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> handleDeleteRole(@PathVariable long id) {

        roleService.handleDeleteRole(id);

        SuccessCode successCode = SuccessCode.DELETED;
        return ResponseEntity.ok().body(ApiResponse.<Void>builder().statusCode(successCode.getStatusCode().value()).message(successCode.getMessage()).data(null).build());
    }

    ;

}
