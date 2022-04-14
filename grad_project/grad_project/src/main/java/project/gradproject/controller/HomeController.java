package project.gradproject.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.domain.user.User;
import project.gradproject.service.StoreService;
import project.gradproject.service.UserService;
import project.gradproject.service.WaitingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final StoreService storeService;
    private final UserService userService;
    private final WaitingService waitingService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

  /*  @PostMapping("/")
    public String login(@Valid @ModelAttribute MemberForm member,
                        BindingResult bindingResult,
                        HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "home";
        }

        if(member.getType().equals("store")){
            Store loginStore = storeService.login((member.getLoginId()), member.getPassword());

            //로그인 실패시
            if(loginStore==null){
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
                return "home";
            }

            // 로그인 성공시
            //세션 있으면 세션 반환, 없으면 신규세션 생성해서 반환
            loginStore.setStoreStatus(StoreStatus.OPEN);
            HttpSession session = request.getSession();
            //세션에 로그인 회원 정보 보관
            session.setAttribute("loginStore", loginStore);

            return "redirect:/store";
        }
        if(member.getType().equals("user")){

        }


        return "home";
        *//*

        if(type.equals("user")) {
            return "redirect:/";
        }
        else if(type.equals("store")){
            // 로그인 인증과정을 거치고~~~~~
            if( storeService.loginCheck(username, password)!=null) {
                Store store = storeService.loginCheck(username, password);
                store.setStoreStatus(StoreStatus.OPEN);
                return "redirect:/store/"+store.getId();
            }
        }
        return "redirect:/";*//*
    }*/
    @PostMapping("/")
    public String login(@Valid @ModelAttribute MemberForm member,
                        BindingResult bindingResult,
                        HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "home";
        }

        if(member.getType().equals("store")){
            Store loginStore = storeService.login((member.getLoginId()), member.getPassword());

            //로그인 실패시
            if(loginStore==null){
                bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
                return "home";
            }

            // 로그인 성공시
            //세션 있으면 세션 반환, 없으면 신규세션 생성해서 반환
            HttpSession session = request.getSession();
            session.setAttribute("loginStoreId",loginStore.getId());


            return "redirect:/store";
        }
        if(member.getType().equals("user")){

        }


        return "home";
        /*

        if(type.equals("user")) {
            return "redirect:/";
        }
        else if(type.equals("store")){
            // 로그인 인증과정을 거치고~~~~~
            if( storeService.loginCheck(username, password)!=null) {
                Store store = storeService.loginCheck(username, password);
                store.setStoreStatus(StoreStatus.OPEN);
                return "redirect:/store/"+store.getId();
            }
        }
        return "redirect:/";*/
    }
}
