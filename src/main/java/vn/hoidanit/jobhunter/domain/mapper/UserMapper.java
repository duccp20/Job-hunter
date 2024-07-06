package vn.hoidanit.jobhunter.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import vn.hoidanit.jobhunter.domain.DTO.Request.User.UserCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.User.UserRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.User.UserUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.company.CompanyUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserUpdateResponse;
import vn.hoidanit.jobhunter.domain.entity.Company;
import vn.hoidanit.jobhunter.domain.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUserFromUserCreationRequest(UserCreationRequest user);

    @Mapping(target = "phone", ignore = true)
    @Mapping(source = "user.company", target = "company")
    UserCreationResponse toUserCreationResponseFromUser(User user);


    @Mapping(target = "password", ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUserFromUserRequest(UserRequest userRequest);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    @Mapping(target = "password", ignore = true)
    UserUpdateResponse toUserUpdateResponse(User user);

}
