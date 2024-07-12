package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.Request.resume.ResumeRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.resume.ResumeUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.job.JobGetResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeGetResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.resume.ResumeUpdateResponse;
import vn.hoidanit.jobhunter.domain.entity.Job;
import vn.hoidanit.jobhunter.domain.entity.Resume;
import vn.hoidanit.jobhunter.service.ResumeService;
import vn.hoidanit.jobhunter.util.Enum.SuccessCode;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ApiResponse<ResumeCreationResponse>> handleCreateResume(@Valid @RequestBody ResumeRequest request) {

        SuccessCode successCode = SuccessCode.CREATED;
        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<ResumeCreationResponse>builder()
                        .statusCode(successCode.getCode())
                        .message(successCode.getMessage())
                        .data(resumeService.handleCreateResume(request))
                        .build()

        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ResumeUpdateResponse>> handleUpdateResume(
            @RequestBody ResumeUpdateRequest request
    ) {
        SuccessCode updatedSuccess = SuccessCode.UPDATED;
        return ResponseEntity.ok(
                ApiResponse.<ResumeUpdateResponse>builder()
                        .data(resumeService.handleUpdateResume(request))
                        .message(updatedSuccess.getMessage())
                        .statusCode(updatedSuccess.getCode())
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> handleDeleteResume(
            @PathVariable long id
    ) {
        resumeService.handleDeleteResume(id);
        SuccessCode deletedSuccess = SuccessCode.DELETED;
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .statusCode(deletedSuccess.getCode())
                        .message(deletedSuccess.getMessage())
                        .data(null)
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ResumeGetResponse>> handleGetResume(
            @PathVariable long id
    ) {
        SuccessCode getSuccess = SuccessCode.GET_SUCCESS;
        return ResponseEntity.ok(
                ApiResponse.<ResumeGetResponse>builder()
                        .statusCode(getSuccess.getCode())
                        .message(getSuccess.getMessage())
                        .data(resumeService.handleGetResume(id))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationDTO>> handleGetAllResumes(
            @Filter Specification<Resume> spec,
            Pageable pageable
    ) {

        PaginationDTO paginationDTO = resumeService.handleGetAllResumes(spec, pageable);
        SuccessCode getSuccess = SuccessCode.GET_SUCCESS;
        return ResponseEntity.ok(
                ApiResponse.<PaginationDTO>builder()
                        .statusCode(getSuccess.getCode())
                        .message(getSuccess.getMessage())
                        .data(paginationDTO)
                        .build()
        );
    }

    @PostMapping("/by-user")
    public ResponseEntity<ApiResponse<PaginationDTO>> handleGetAllResumesByUser(
            Pageable pageable
    ) {

        PaginationDTO paginationDTO = resumeService.handleGetAllResumesByUser(pageable);
        SuccessCode getSuccess = SuccessCode.GET_SUCCESS;
        return ResponseEntity.ok(
                ApiResponse.<PaginationDTO>builder()
                        .statusCode(getSuccess.getCode())
                        .message(getSuccess.getMessage())
                        .data(paginationDTO)
                        .build()
        );
    }
}
