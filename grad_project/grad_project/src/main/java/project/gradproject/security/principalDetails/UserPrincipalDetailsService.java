package project.gradproject.security.principalDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.gradproject.domain.user.User;
import project.gradproject.repository.UserRepository;
import project.gradproject.security.PrincipalDetails;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements PrincipalDetailsService {

    public final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return new PrincipalDetails(user);
    }
}
