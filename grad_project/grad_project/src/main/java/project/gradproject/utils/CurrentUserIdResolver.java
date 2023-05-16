package project.gradproject.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import project.gradproject.annotation.CurrentUserId;
import project.gradproject.service.user.UserLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class CurrentUserIdResolver implements HandlerMethodArgumentResolver {

    private final UserLoginService userLoginService;

    //HandlerMethodArgumentResolver 사용시 오버라이딩 해야 하는 메소드
    //현재 파라미터를 resolver 이 지원하는지에 대한 true/false 값 리턴
    @Override
    public boolean supportsParameter(
            MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUserId.class);
    }

    //실제로 바인딩을 할 객체를 리턴 한다.
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        HttpSession session = ((HttpServletRequest) nativeWebRequest).getSession(false);
        return userLoginService.getCurrentMemberId(session);
    }
}
