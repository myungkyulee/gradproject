package project.gradproject.security;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import project.gradproject.security.principalDetails.PrincipalDetailsServiceAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public MyUsernamePasswordAuthenticationFilter(PrincipalDetailsServiceAdapter principalDetailsServiceAdapter,
                                                  PasswordEncoder passwordEncoder) {
        super(new ProviderManager(new MyAuthenticationProvider(principalDetailsServiceAdapter, passwordEncoder)));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        username = (username != null) ? username : "";
        username = username.trim();
        String password = obtainPassword(request);
        password = (password != null) ? password : "";

        String type = request.getParameter("type");

        UsernamePasswordAuthenticationToken authRequest = new MyAuthenticationToken(username, password, type);

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
