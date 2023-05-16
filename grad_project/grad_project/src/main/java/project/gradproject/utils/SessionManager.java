package project.gradproject.utils;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class SessionManager {

    public Long getCurrentMemberId(HttpSession session, String type){
        return (Long) session.getAttribute(type);
    }

    public void logout(HttpSession session, String type){
        session.removeAttribute(type);
    }

    public void login(HttpSession session, String type, Long id){
        session.setAttribute(type, id);
    }
}
