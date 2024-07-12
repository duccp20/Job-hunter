package vn.hoidanit.jobhunter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.company.CompanyResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.Meta;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.entity.Company;
import vn.hoidanit.jobhunter.domain.entity.User;
import vn.hoidanit.jobhunter.domain.mapper.CompanyMapper;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.repository.CompanyRepository;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    private final UserRepository userRepository;

    @Override
    public CompanyResponse createCompany(CompanyRequest companyRequest) {

        boolean existedCompany = companyRepository.existsByName(companyRequest.getName());

        if(existedCompany)
            throw new AppException(ErrorCode.COMPANY_EXISTED);

        Company company = companyMapper.toCompany(companyRequest);

        return companyMapper.toCompanyResponse(companyRepository.save(company));
    }

    @Override
    public PaginationDTO getAllCompany(Specification<Company> spec, Pageable pageable) {

        Page<Company> listPage = companyRepository.findAll(spec, pageable);

        List<Company> companyList = listPage.getContent();

        List<CompanyResponse> res = new ArrayList<>();
        for (Company c : companyList) {
            res.add(companyMapper.toCompanyResponse(c));
        }

        Meta meta = Meta.builder()
                .current(listPage.getNumber() + 1)
                .pageSize(listPage.getNumberOfElements())
                .totalPages(listPage.getTotalPages())
                .totalItems(listPage.getTotalElements())
                .build();

        return PaginationDTO.builder()
                .meta(meta)
                .result(res)
                .build();
    }

    @Override
    public CompanyResponse updateCompany(CompanyUpdateRequest companyRequest) {

        Company currentCompany = companyRepository.findById(companyRequest.getId()).orElseThrow(
                () -> new AppException(ErrorCode.COMPANY_NOT_EXISTED)
        );

        companyMapper.updateCompany(currentCompany, companyRequest);

        return companyMapper.toCompanyResponse(companyRepository.save(currentCompany));
    }

    @Override
    public void deleteCompany(long id) {

        boolean existedCompany = companyRepository.existsById(id);
        if(!existedCompany)
            throw new AppException(ErrorCode.COMPANY_NOT_EXISTED);

        List<User> users = userRepository.findByCompanyId(id);
        if(!users.isEmpty()) {
            userRepository.deleteAll(users);
        }

        companyRepository.deleteById(id);
    }

    @Override
    public Company getCompanyById(long id) {

        Optional<Company> company = companyRepository.findById(id);

        return company.isPresent() ? company.get() : null;
    }

    @Override
    public CompanyResponse handleGetCompany(long id) {

        Company company = companyRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.COMPANY_NOT_EXISTED)
        );

        return companyMapper.toCompanyResponse(company);
    }

//    @Override
//    public boolean existedCompany(long id) {
//        return companyRepository.existsById(id);
//    }

}
