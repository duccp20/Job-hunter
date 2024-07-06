package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.job.JobUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.skill.SkillRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobGetResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobUpdateResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.skill.SkillResponse;
import vn.hoidanit.jobhunter.domain.entity.Job;
import vn.hoidanit.jobhunter.domain.entity.Skill;
import vn.hoidanit.jobhunter.service.JobService;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.util.Enum.SuccessCode;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/jobs")
public class JobController {
    private final JobService jobService;
    @PostMapping
    public ResponseEntity<ApiResponse<JobCreationResponse>> handleCreateJob(@Valid @RequestBody JobCreationRequest jobCreationRequest) {

        SuccessCode successCode = SuccessCode.CREATED;
        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<JobCreationResponse>builder()
                        .statusCode(successCode.getStatusCode().value())
                        .message(successCode.getMessage())
                        .data(jobService.handleCreateJob(jobCreationRequest))
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<JobUpdateResponse>> handleUpdateJob(@Valid @RequestBody JobUpdateRequest jobUpdateRequest) {

        SuccessCode updatedSuccess = SuccessCode.UPDATED;
        return ResponseEntity.ok(
                ApiResponse.<JobUpdateResponse>builder()
                        .statusCode(updatedSuccess.getCode())
                        .message(updatedSuccess.getMessage())
                        .data(jobService.handleUpdateJob(jobUpdateRequest))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationDTO>> handleGetAllJobs(
            @Filter Specification<Job> spec,
            Pageable pageable
            ) {

        PaginationDTO paginationDTO = jobService.handleGetAllJobs(spec, pageable);
        SuccessCode getSuccess = SuccessCode.GET_SUCCESS;
        return ResponseEntity.ok(
                ApiResponse.<PaginationDTO>builder()
                        .statusCode(getSuccess.getCode())
                        .message(getSuccess.getMessage())
                        .data(paginationDTO)
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<JobGetResponse>> handleGetJob(
          @PathVariable long id
    ) {
        SuccessCode getSuccess = SuccessCode.GET_SUCCESS;
        return ResponseEntity.ok(
                ApiResponse.<JobGetResponse>builder()
                        .statusCode(getSuccess.getCode())
                        .message(getSuccess.getMessage())
                        .data(jobService.handleGetJob(id))
                        .build()
        );
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> handleDeleteJob(@PathVariable long id) {

        jobService.handleDeleteJob(id);

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
