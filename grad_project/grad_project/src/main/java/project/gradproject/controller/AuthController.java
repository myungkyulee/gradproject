package project.gradproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import project.gradproject.annotation.LoginCheck;
import project.gradproject.domain.MemberLoginForm;
import project.gradproject.domain.MemberType;
import project.gradproject.service.store.LoginService;
import project.gradproject.service.store.LoginServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {
    public final String USER = "user";
    public final String STORE = "store";

    public final LoginServiceFactory loginServiceFactory;

    @GetMapping("/login")
    public String loginForm() {
        log.debug("[HomeController] loginForm");
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute MemberLoginForm member,
                        BindingResult bindingResult) {
        log.debug("[HomeController] login");

        if (bindingResult.hasErrors()) {
            return "login";
        }

        LoginService loginService = loginServiceFactory.getLoginService(member.getType());
        loginService.login(member.getEmail(), member.getPassword());

        return "redirect:/" + member.getType();
    }

    @PostMapping("/user/logout")
    @LoginCheck(type = MemberType.USER)
    public String userLogout() {
        LoginService loginService = loginServiceFactory.getLoginService(USER);

        loginService.logout();

        return "redirect:/login";
    }

    @PostMapping("/store/logout")
    @LoginCheck(type = MemberType.STORE)
    public String storeLogout() {
        LoginService loginService = loginServiceFactory.getLoginService(STORE);

        loginService.logout();

        return "redirect:/login";
    }
}
