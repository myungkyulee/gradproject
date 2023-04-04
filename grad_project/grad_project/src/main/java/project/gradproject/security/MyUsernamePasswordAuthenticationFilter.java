package project.gradproject.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import project.gradproject.security.principalDetails.PrincipalDetailsService;
import project.gradproject.security.principalDetails.PrincipalDetailsServiceAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/login", "POST");




    public MyUsernamePasswordAuthenticationFilter(AuthenticationManager MyAuthenticationManager) {
        super(MyAuthenticationManager);
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String type = request.getParameter("type");

        // 추출한 유저 타입에 따라 UserDetailsService를 동적으로 변경
        PrincipalDetailsService service = new PrincipalDetailsServiceAdapter().getService(type);



        if ("user".equals(userType)) {
            userDetailsService = new CustomUserDetailsService();
        } else if ("store".equals(userType)) {
            userDetailsService = new CustomStoreDetailsService();
        }

        // UserDetailsService를 이용하여 인증 객체 생성
        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request, userDetailsService);

        // AuthenticationManager를 이용하여 인증 처리
        AuthenticationManager authenticationManager = getAuthenticationManager();
        Authentication authResult = authenticationManager.authenticate(authRequest);

        return authResult;
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request, UserDetailsService userDetailsService) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        return authRequest;
    }
}
