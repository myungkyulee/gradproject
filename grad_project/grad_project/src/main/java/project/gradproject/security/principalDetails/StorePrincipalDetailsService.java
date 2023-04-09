package project.gradproject.security.principalDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.gradproject.domain.store.Store;
import project.gradproject.repository.StoreRepository;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 loadUserByUsername 함수가 실행됨
@Service
@RequiredArgsConstructor
public class StorePrincipalDetailsService implements PrincipalDetailsService {

    public final StoreRepository storeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Store store = storeRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return new PrincipalDetails(store);
    }
}
