package project.gradproject.service.store;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoginServiceFactory {
    public Map<String, LoginService> services;

    public LoginServiceFactory(Map<String, LoginService> services){
        this.services = services;
    }

    public LoginService getLoginService(String type){
        type += "LoginService";
        return services.get(type);
    }
}
