package project.gradproject.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.gradproject.domain.user.User;
import project.gradproject.exception.LoginException;
import project.gradproject.exception.MyCustomException;
import project.gradproject.service.store.LoginService;
import project.gradproject.utils.PasswordEncoder;
import project.gradproject.utils.SessionManager;

import javax.servlet.http.HttpSession;

import static project.gradproject.exception.ErrorCode.NOT_FOUND_USER;
import static project.gradproject.exception.ErrorCode.PASSWORD_UN_MATCH;

@Service
@RequiredArgsConstructor
public class UserLoginService implements LoginService {

    private static final String USER_ID = "USER_ID";
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;

    @Override
    public void login(HttpSession session, String email, String password) {
        try {
            User user = userService.findOneByEmail(email);

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new LoginException(PASSWORD_UN_MATCH);
            }

            sessionManager.login(session, USER_ID, user.getId());

        } catch (LoginException e) {
            throw e;
        } catch (MyCustomException e) {
            throw new LoginException(NOT_FOUND_USER);
        }
    }

    @Override
    public void logout(HttpSession session) {
        sessionManager.logout(session, USER_ID);
    }

    @Override
    public Long getCurrentMemberId(HttpSession session) {
        return sessionManager.getCurrentMemberId(session, USER_ID);
    }

}
