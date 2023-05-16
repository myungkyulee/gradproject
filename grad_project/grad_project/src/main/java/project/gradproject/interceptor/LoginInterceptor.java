package project.gradproject.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import project.gradproject.annotation.LoginCheck;
import project.gradproject.domain.MemberType;
import project.gradproject.exception.AuthorizedException;
import project.gradproject.exception.ErrorCode;
import project.gradproject.service.store.LoginService;
import project.gradproject.service.store.LoginServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final LoginServiceFactory loginServiceFactory;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        if (handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(LoginCheck.class)) {

            LoginCheck loginCheck = ((HandlerMethod) handler).getMethodAnnotation(LoginCheck.class);
            MemberType type = Objects.requireNonNull(loginCheck).type();

            LoginService loginService = null;

            if (MemberType.USER.equals(type)) {
                loginService = loginServiceFactory.getLoginService("user");
            } else if (MemberType.STORE.equals(type)) {
                loginService = loginServiceFactory.getLoginService("store");
            }

            Long memberId = loginService.getCurrentMemberId();

            if (memberId == null) {
                throw new AuthorizedException(ErrorCode.UN_AUTHORIZED_MEMBER);
            }
        }

        return true;
    }
}
