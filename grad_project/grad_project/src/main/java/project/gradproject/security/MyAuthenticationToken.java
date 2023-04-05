package project.gradproject.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String type;

    public MyAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public MyAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public MyAuthenticationToken(Object principal, Object credentials, String type){
        super(principal, credentials);
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

}
