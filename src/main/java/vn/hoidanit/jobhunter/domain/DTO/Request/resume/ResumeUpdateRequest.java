package vn.hoidanit.jobhunter.domain.DTO.Request.resume;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import vn.hoidanit.jobhunter.util.Enum.ResumeStateEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeUpdateRequest {

    private Long id;
    @Enumerated(EnumType.STRING)
    ResumeStateEnum status;
}
