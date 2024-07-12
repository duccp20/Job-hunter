package vn.hoidanit.jobhunter.service.impl;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.DTO.Request.user.UserCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.user.UserUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserUpdateResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.company.CompanyResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.Meta;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.entity.Company;
import vn.hoidanit.jobhunter.domain.entity.Role;
import vn.hoidanit.jobhunter.domain.entity.User;
import vn.hoidanit.jobhunter.domain.mapper.UserMapper;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.repository.RoleRepository;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.service.RoleService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.service.UserService;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    CompanyService companyService;
    private final RoleRepository roleRepository;

    RoleService roleService;


    @Override
    public UserCreationResponse handleCreateUser(UserCreationRequest user) {

        boolean existedUserByEmail = userRepository.existsByEmail(user.getEmail());

        if (existedUserByEmail)
            throw new AppException(ErrorCode.USER_EXISTED);

        User newUser = userMapper.toUserFromUserCreationRequest(user);

        if (user.getCompany() != null) {
            Company company = companyService.getCompanyById(user.getCompany().getId());
            newUser.setCompany(company);
        }

        if(user.getRole() != null) {
            Role role = roleService.handleGetRoleById(user.getRole().getId());
            newUser.setRole(role);
        }

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(newUser);

        return userMapper.toUserCreationResponseFromUser(newUser);
    }

    @Override
    public PaginationDTO handleFetchAllUser(Specification<User> specification, Pageable pageable) {

        Page<User> userPage = userRepository.findAll(specification, pageable);

        List<User> userList = userPage.getContent();

        List<UserResponse> userResponses = new ArrayList<>();
        for (User u : userList) {
            userResponses.add(userMapper.toUserResponse(u));
        }

        Meta meta = Meta.builder()
                .current(userPage.getNumber() + 1)
                .pageSize(userPage.getSize())
                .totalItems(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .build();

        return PaginationDTO.builder()
                .meta(meta)
                .result(userResponses)
                .build();
    }


    @Override
    public UserResponse handleGetUserById(long id) {
        User user = userRepository.findUserById((Long) id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    public void handleDeleteUser(long id) {

        boolean existedUser = userRepository.existsById(id);
        if (existedUser) {
            userRepository.deleteById(id);
        } else throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }


    @Override
    public UserUpdateResponse handleUpdateUser(UserUpdateRequest userUpdateRequest) {

        User currentUser = userRepository.findById(userUpdateRequest.getId()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        userMapper.updateUser(currentUser, userUpdateRequest);

        if (userUpdateRequest.getCompany() != null) {
            long id = userUpdateRequest.getCompany().getId();
            Company companyInDB = companyService.getCompanyById(id);
            currentUser.setCompany(companyInDB);
        }

        if (userUpdateRequest.getRole() != null) {
            Role role = roleService.handleGetRoleById(userUpdateRequest.getRole().getId());
            currentUser.setRole(role);
        }

        return userMapper.toUserUpdateResponse(userRepository.save(currentUser));
    }

    @Override
    public User handleGetUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void handleUpdateRefreshToken(String email, String refreshToken) {
        User user = userRepository.findUserByEmail(email);

        if (user == null)
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        user.setRefreshToken(refreshToken);

        userRepository.save(user);
    }

    @Override
    public User handleFindUserByRefreshTokenAndEmail(String refreshToken, String email) {
        return this.userRepository.findUserByRefreshTokenAndEmail(refreshToken, email);
    }

    @Override
    public List<User> handleFindUserByCompanyId(long id) {
        return userRepository.findByCompanyId(id);
    }

    @Override
    public void deleteAllUser(long id) {
        userRepository.deleteAllByCompanyId(id);
    }


}
