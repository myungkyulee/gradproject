package project.gradproject.service.store;

import javax.servlet.http.HttpSession;

public interface LoginService {

    void login(HttpSession session, String email, String password);
    void logout(HttpSession session);
    Long getCurrentMemberId(HttpSession session);
}
