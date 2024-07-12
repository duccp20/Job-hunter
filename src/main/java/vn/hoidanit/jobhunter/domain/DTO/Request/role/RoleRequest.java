package vn.hoidanit.jobhunter.domain.DTO.Request.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.domain.DTO.Request.permission.PermissionCreationRequest;

import java.util.List;

@Setter
@Getter
public class RoleRequest {

    @NotBlank(message = "Name can not be empty!")
    String name;
    String description;
    boolean active;
    List<PermissionCreationRequest> permissions;
}
