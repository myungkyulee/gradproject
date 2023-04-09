package project.gradproject.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // resource 자원에 인증을 하지 않도록 설정
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .addFilterBefore(myUsernamePasswordAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/store/**").hasRole("STORE")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                //.defaultSuccessUrl("/store")
                .successHandler((request, response, authentication) -> {
                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                    for (GrantedAuthority authority : authorities) {
                        if (authority.getAuthority().equals("ROLE_USER")) {
                            response.sendRedirect("/user");
                            return;
                        } else if (authority.getAuthority().equals("ROLE_STORE")) {
                            response.sendRedirect("/store");
                            return;
                        }
                    }
                })
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/login");

    }
}
