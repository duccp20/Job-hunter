package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.hoidanit.jobhunter.domain.entity.Company;
import vn.hoidanit.jobhunter.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsUserById(Long id);

    User findUserById(long id);

    boolean existsByEmail(String email);

    Optional<User> findUserById(Long id);

    User findUserByName(String username);

    User findUserByEmail(String email);

    User findUserByRefreshTokenAndEmail(String refreshToken, String email);

    List<User> findByCompanyId(long id);

    void deleteAllByCompanyId(long id);

}
