package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.Request.User.UserCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.User.UserUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.entity.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.Enum.SuccessCode;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/users")
public class UserController {

    UserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<UserCreationResponse>> handleCreateUser(@RequestBody UserCreationRequest user) {

        SuccessCode successCode = SuccessCode.CREATED;

        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<UserCreationResponse>builder()
                        .statusCode(successCode.getCode())
                        .message(successCode.getMessage())
                        .data(userService.handleCreateUser(user))
                        .build());
    }

//    @GetMapping("")
//    public ResponseEntity<ApiResponse<List<UserResponse>>> handleGetAllUser() {
//        List<UserResponse> list = userService.handleFetchAllUser(Specification<User> spec, Pag);
//        return ResponseEntity.ok().body(
//                ApiResponse.<List<UserResponse>>builder()
//                        .data(list)
//                        .build()
//        );
//    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<UserResponse>> handleGetUserById(@PathVariable long id) {
        return ResponseEntity.ok().body(
                ApiResponse.<UserResponse>builder()
                        .data(userService.handleGetUserById(id))
                        .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<UserResponse>> handleUpdateUser(
            @PathVariable long id,
            @RequestBody UserUpdateRequest userUpdateRequest) {

        return ResponseEntity.ok().body(
                ApiResponse.<UserResponse>builder()
                        .data(userService.handleUpdateUser(id, userUpdateRequest))
                        .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> handleDeleteUser(@PathVariable long id) {

        userService.handleDeleteUser(id);

        SuccessCode successCode = SuccessCode.DELETED;

        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.builder()
                        .statusCode(successCode.getCode())
                        .message(successCode.getMessage())
                        .build()
        );
    }

    @GetMapping(value = "")
    public ResponseEntity<ApiResponse<PaginationDTO>> handleGetAllUser(
            @Filter Specification<User> spec,
//            @RequestParam(value = "current", defaultValue = "1") String sCurrent,
//            @RequestParam(value = "pageSize", defaultValue = "5") String sPageSize
            Pageable pageable
            ) {

//        int current = Integer.parseInt(sCurrent) - 1;
//        int pageSize = Integer.parseInt(sPageSize);

//        Pageable pageable = PageRequest.of(current, pageSize);
        PaginationDTO list  = userService.handleFetchAllUser(spec, pageable);
        return ResponseEntity.ok(
                ApiResponse.<PaginationDTO>builder()
                        .statusCode(200)
                        .data(list)
                        .build()
        );
    }
}
