package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.company.CompanyResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.entity.Company;

import java.util.Optional;

public interface CompanyService {

    CompanyResponse createCompany(CompanyRequest companyRequest);

    PaginationDTO getAllCompany(Specification<Company> spec, Pageable pageable);

    CompanyResponse updateCompany(CompanyUpdateRequest companyRequest);

    void deleteCompany(long id);

    Company getCompanyById(long id);
}
