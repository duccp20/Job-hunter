package vn.hoidanit.jobhunter.domain.DTO.Request.permission;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequest {
    @NotBlank(message = "Name can not be empty!")
    String name;
    @NotBlank(message = "Api Path can not be empty!")
    String apiPath;
    @NotBlank(message = "Method can not be empty!")
    String method;
    String module;
}
