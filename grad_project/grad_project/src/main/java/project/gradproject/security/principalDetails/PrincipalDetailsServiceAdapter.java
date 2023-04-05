package project.gradproject.security.principalDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PrincipalDetailsServiceAdapter {
    private final Map<String, PrincipalDetailsService> serviceMappingMap;

    public UserDetailsService getService(String type){
        String key = type + "PrincipalDetailsService";
        return serviceMappingMap.get(key);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : serviceMappingMap.keySet()) {
            sb.append(s);
        }
        return sb.toString();
    }

    public int count(){
        return serviceMappingMap.size();
    }
}
