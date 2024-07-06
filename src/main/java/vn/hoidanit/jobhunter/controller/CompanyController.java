package vn.hoidanit.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.ApiResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.company.CompanyResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.entity.Company;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.util.Enum.SuccessCode;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponse>> handleCreateCompany(@Valid @RequestBody CompanyRequest request) {

        SuccessCode successCode = SuccessCode.CREATED;
        return ResponseEntity.status(successCode.getStatusCode()).body(
                ApiResponse.<CompanyResponse>builder()
                        .statusCode(successCode.getCode())
                        .message(successCode.getMessage())
                        .data(companyService.createCompany(request))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationDTO>> handleGetAllCompany(
            @Filter Specification<Company> spec,
//            @RequestParam(defaultValue = "1") String current,
//            @RequestParam(defaultValue = "5") String pageSize
            Pageable pageable
    ) {

//        int IntCurrent = Integer.parseInt(current);
//        int IntPageSize = Integer.parseInt(pageSize);
//
//        Pageable pageable = PageRequest.of(IntCurrent - 1, IntPageSize);

        PaginationDTO list = companyService.getAllCompany(spec, pageable);

        return ResponseEntity.ok(
                ApiResponse.<PaginationDTO>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Call API Successfully")
                        .data(list)
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CompanyResponse>> handleUpdateCompany(@Valid @RequestBody CompanyUpdateRequest request) {

        return ResponseEntity.ok(
                ApiResponse.<CompanyResponse>builder()
                        .statusCode(200)
                        .message("Updated Successfully!")
                        .data(companyService.updateCompany(request))
                        .build()
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> handleDeleteCompany(@PathVariable long id) {

        companyService.deleteCompany(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .statusCode(200)
                        .message(SuccessCode.DELETED.getMessage())
                        .data(null)
                        .build()
        );
    }
}
