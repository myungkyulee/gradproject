package project.gradproject.security.principalDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PrincipalDetailsServiceAdapter {
    private final Map<String, PrincipalDetailsService> serviceMappingMap = new HashMap<>();

    public PrincipalDetailsService getService(String type){
        String key = type + "PrincipalDetailsService";
        return serviceMappingMap.get(type);
    }
}
