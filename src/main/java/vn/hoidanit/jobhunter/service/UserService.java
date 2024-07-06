package vn.hoidanit.jobhunter.service;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.hoidanit.jobhunter.domain.DTO.Request.User.UserCreationRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.User.UserRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.User.UserUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserCreationResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.User.UserUpdateResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.pagination.PaginationDTO;
import vn.hoidanit.jobhunter.domain.entity.User;


import javax.swing.text.html.parser.Entity;
import java.util.List;


public interface UserService {

    UserCreationResponse handleCreateUser(UserCreationRequest user);

   PaginationDTO handleFetchAllUser(Specification<User> specification, Pageable page);

    UserResponse handleGetUserById(long id);

    void handleDeleteUser(long id);

    UserUpdateResponse handleUpdateUser(long id, UserUpdateRequest userUpdateRequest);

    User handleGetUserByEmail(String email);

    void handleUpdateRefreshToken(String email, String refreshToken);

    User handleFindUserByRefreshTokenAndEmail(String refreshToken, String email);

    List<User> handleFindUserByCompanyId(long id);

    void deleteAllUser(long id);
}
