package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.Request.subscriber.SubscriberRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.subscriber.SubscriberUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.subscriber.SubscriberResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.subscriber.SubscriberUpdateResponse;
import vn.hoidanit.jobhunter.service.SubscriberService;
import vn.hoidanit.jobhunter.util.Enum.SuccessCode;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubscriberResponse>> handleCreateSubscriber(
            @Valid @RequestBody SubscriberRequest subscriberRequest
    ) {
        SuccessCode successCode = SuccessCode.CREATED;
        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<SubscriberResponse>builder()
                        .statusCode(successCode.getCode())
                        .message(successCode.getMessage())
                        .data(subscriberService.handleCreateSubscriber(subscriberRequest))
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<SubscriberUpdateResponse>> handleUpdateSubscriber(
            @Valid @RequestBody SubscriberUpdateRequest subscriberRequest
    ) {
        SuccessCode successCode = SuccessCode.UPDATED;
        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<SubscriberUpdateResponse>builder()
                        .statusCode(successCode.getCode())
                        .message(successCode.getMessage())
                        .data(subscriberService.handleUpdateSubscriber(subscriberRequest))
                        .build()
        );
    }

    @PostMapping("/skills")
    public ResponseEntity<ApiResponse<SubscriberResponse>> handleGetSubscriberSkillsByEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        SuccessCode successCode = SuccessCode.GET_SUCCESS;
        return ResponseEntity.ok(
                ApiResponse.<SubscriberResponse>builder()
                        .statusCode(successCode.getCode())
                        .message(successCode.getMessage())
                        .data(subscriberService.handleGetSubscriberSkillsByEmail(email))
                        .build()
        );
    }

}
