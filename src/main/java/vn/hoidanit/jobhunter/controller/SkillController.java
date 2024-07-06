package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.Request.skill.SkillRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.skill.SkillResponse;
import vn.hoidanit.jobhunter.domain.entity.Skill;
import vn.hoidanit.jobhunter.domain.entity.User;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.util.Enum.SuccessCode;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/skills")
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<ApiResponse<SkillResponse>> handleCreateSkill(@Valid @RequestBody SkillRequest skillRequest) {

        SuccessCode successCode = SuccessCode.CREATED;
        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<SkillResponse>builder()
                        .statusCode(successCode.getStatusCode().value())
                        .message(successCode.getMessage())
                        .data(skillService.handleCreateSkill(skillRequest))
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<SkillResponse>> handleUpdateSkill(@Valid @RequestBody SkillRequest skillRequest) {

        SuccessCode successCode = SuccessCode.UPDATED;
        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<SkillResponse>builder()
                        .statusCode(successCode.getStatusCode().value())
                        .message(successCode.getMessage())
                        .data(skillService.handleUpdateSkill(skillRequest))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationDTO>> handleGetAllSkill(
            @Filter Specification<Skill> spec,
            Pageable pageable
    ) {
        SuccessCode successCode = SuccessCode.GET_SUCCESS;

        PaginationDTO paginationDTO = skillService.handleGetAllSkill(spec, pageable);
        return ResponseEntity.status(successCode.getStatusCode())
                .body(
                        ApiResponse.<PaginationDTO>builder()
                                .message(successCode.getMessage())
                                .statusCode(successCode.getStatusCode().value())
                                .data(paginationDTO)
                                .build()
                );

    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> handleDeleteSkill(@PathVariable long id) {

        skillService.handleDeleteSkill(id);

        SuccessCode successCode = SuccessCode.DELETED;
        return ResponseEntity.ok().body(
                ApiResponse.<Void>builder()
                        .statusCode(successCode.getStatusCode().value())
                        .message(successCode.getMessage())
                        .data(null)
                        .build()
        );
    };
}
