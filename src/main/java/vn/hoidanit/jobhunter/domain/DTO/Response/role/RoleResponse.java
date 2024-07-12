package vn.hoidanit.jobhunter.domain.DTO.Response.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.domain.DTO.Response.permission.PermissionResponse;
import vn.hoidanit.jobhunter.domain.entity.Permission;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;
    boolean active;
    List<PermissionResponse> permissions;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;

}
