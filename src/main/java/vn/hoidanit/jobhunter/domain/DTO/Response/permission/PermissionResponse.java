package vn.hoidanit.jobhunter.domain.DTO.Response.permission;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponse {
    long id;
    String name;
    String apiPath;
    String method;
    String module;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;
}
