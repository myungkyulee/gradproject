package project.gradproject.service.store;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;
import project.gradproject.exception.LoginException;
import project.gradproject.exception.MyCustomException;
import project.gradproject.service.user.UserService;
import project.gradproject.utils.PasswordEncoder;
import project.gradproject.utils.SessionManager;

import javax.servlet.http.HttpSession;

import static project.gradproject.exception.ErrorCode.NOT_FOUND_USER;
import static project.gradproject.exception.ErrorCode.PASSWORD_UN_MATCH;

@Service
@RequiredArgsConstructor
public class StoreLoginService implements LoginService {

    private static final String STORE_ID = "STORE_ID";
    private final StoreService storeService;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;

    @Override
    public void login(HttpSession session, String email, String password) {
        try {
            Store store = storeService.findOneByEmail(email);

            if (!passwordEncoder.matches(password, store.getPassword())) {
                throw new LoginException(PASSWORD_UN_MATCH);
            }

            sessionManager.login(session, STORE_ID, store.getId());
        } catch (LoginException e) {
            throw e;
        } catch (MyCustomException e) {
            throw new LoginException(NOT_FOUND_USER);
        }
    }

    @Override
    public void logout(HttpSession session) {
        sessionManager.logout(session, STORE_ID);
    }

    @Override
    public Long getCurrentMemberId(HttpSession session) {
        return sessionManager.getCurrentMemberId(session, STORE_ID);
    }

}
