package vn.hoidanit.jobhunter.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.hoidanit.jobhunter.domain.DTO.Request.user.UserCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.user.UserRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.user.UserUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserUpdateResponse;
import vn.hoidanit.jobhunter.domain.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "resumes", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUserFromUserCreationRequest(UserCreationRequest user);


    @Mapping(source = "user.company", target = "company")
    UserCreationResponse toUserCreationResponseFromUser(User user);


    @Mapping(target = "password", ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(target = "resumes", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUserFromUserRequest(UserRequest userRequest);


    @Mapping(target = "resumes", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    UserUpdateResponse toUserUpdateResponse(User user);

}
