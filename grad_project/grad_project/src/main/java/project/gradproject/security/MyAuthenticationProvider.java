package project.gradproject.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.gradproject.security.principalDetails.PrincipalDetailsServiceAdapter;

@Slf4j
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    private final PrincipalDetailsServiceAdapter principalDetailsServiceAdapter;

    public MyAuthenticationProvider(PrincipalDetailsServiceAdapter principalDetailsServiceAdapter,
                                    PasswordEncoder passwordEncoder) {
        super();
        this.principalDetailsServiceAdapter = principalDetailsServiceAdapter;
        setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String type = ((MyAuthenticationToken) authentication).getType();

        UserDetailsService service = principalDetailsServiceAdapter.getService(type);

        log.info("[Service] count = {}", principalDetailsServiceAdapter.count());
        log.info("Service Name = {}", principalDetailsServiceAdapter);

        setUserDetailsService(service);

        return super.authenticate(authentication);
    }
}
