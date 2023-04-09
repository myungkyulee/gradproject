package project.gradproject.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.gradproject.domain.MemberLoginForm;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;
import project.gradproject.service.StoreService;
import project.gradproject.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final StoreService storeService;
    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        log.info("login!!!!!!!!!!!!!!!!!!");
        return "home";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute MemberLoginForm member,
                        BindingResult bindingResult,
                        HttpServletRequest request) {
        log.info("[HomeController] login");

        if (bindingResult.hasErrors()) {
            return "home";
        }

        // store로그인
        if (member.getType().equals("store")) {
            Store loginStore =
                    storeService.login(member.getEmail(), member.getPassword());

            //로그인 실패시
            if (loginStore == null) {
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
                return "home";
            }
            storeService.openStore(loginStore.getId());
            // 로그인 성공시
            //세션 있으면 세션 반환, 없으면 신규세션 생성해서 반환
            HttpSession session = request.getSession();

            session.setAttribute("loginStoreId", loginStore.getId());


            return "redirect:/store";
        }

        // 유저로그인
        if (member.getType().equals("user")) {
            User loginUser = userService.login(member.getEmail(), member.getPassword());

            // 로그인 실패
            if (loginUser == null) {
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");

                return "home";
            }

            // 로그인 성공
            HttpSession session = request.getSession();
            session.setAttribute("loginUserId", loginUser.getId());

            return "redirect:/user";
        }

        return "home";
    }

    @PostMapping("/store/logout")
    public String logoutStore(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return "redirect:/";
        Long storeId = (Long) session.getAttribute("loginStoreId");
        storeService.closeStore(storeId);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }
}
