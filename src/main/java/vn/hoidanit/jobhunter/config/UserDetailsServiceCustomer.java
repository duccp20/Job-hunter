package vn.hoidanit.jobhunter.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vn.hoidanit.jobhunter.domain.entity.User;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;

@Component("userDetailsService")
public class UserDetailsServiceCustomer implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceCustomer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(username);

        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        };

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
//                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

    }
}
