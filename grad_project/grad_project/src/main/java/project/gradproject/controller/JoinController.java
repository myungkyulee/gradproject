package project.gradproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.gradproject.domain.StoreJoinForm;
import project.gradproject.domain.UserJoinForm;
import project.gradproject.domain.store.Address;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.domain.user.User;
import project.gradproject.service.StoreService;
import project.gradproject.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class JoinController {

    private final StoreService storeService;
    private final UserService userService;

    @GetMapping
    public String addForm(){
        return "join/addMemberForm";
    }

    @GetMapping("/addStore")
    public String addStoreForm(@ModelAttribute("member") StoreJoinForm store){


        return "join/addStoreForm";
    }
    @PostMapping("/addStore")
    public String saveStore(@Validated @ModelAttribute("member") StoreJoinForm storeForm, BindingResult result) {
        if (result.hasErrors()) {
            return "join/addStoreForm";
        }
        /*if(storeForm.getTableCount()==null){
            result.rejectValue("tableCount",);
        }*/


        String ad = storeForm.getLocationName();





        Store store = new Store();

        store.setLocationName(ad);
        store.setLocationX(storeForm.getLocationX());
        store.setLocationY(storeForm.getLocationY());
        store.setName(storeForm.getName());
        store.setLoginId(storeForm.getLoginId());
        store.setLoginPassword(storeForm.getPassword());
        store.setTableCount(storeForm.getTableCount());
        store.setStoreStatus(StoreStatus.CLOSED);
        store.setRestTableCount(store.getTableCount());
        store.setInfo(storeForm.getInfo());

        storeService.join(store);


        return "redirect:/";
    }
    @GetMapping("/addStore/location")
    public String storeLocation(@ModelAttribute("store") StoreJoinForm storeJoinForm, Model model){

        if(storeJoinForm.getLocationName()==null) storeJoinForm.setLocationName("위치를 설정해주세요");
        model.addAttribute("user", storeJoinForm);
        return "user/location";
    }


    @PostMapping("/addStore/location")
    public String locationUpdate(@RequestParam("location") String location,
                                 @RequestParam("x") Double x,
                                 @RequestParam("y") Double y,
                                 Model model){

        System.out.println("OK");
        System.out.println(location);
        System.out.println(x);
        System.out.println(y);

        StoreJoinForm storeJoinForm = new StoreJoinForm();
        storeJoinForm.setLocationName(location);
        storeJoinForm.setLocationX(x);
        storeJoinForm.setLocationY(y);
        model.addAttribute("member", storeJoinForm);
        return "join/addStoreForm";
    }


    @GetMapping("/addUser")
    public String addUserForm(@ModelAttribute("member") UserJoinForm user){
        return "join/addUserForm";
    }
    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("member") UserJoinForm userForm, BindingResult result) {

        if (result.hasErrors()) {
            return "join/addUserForm";
        }

        User user = new User();
        user.setName(userForm.getName());
        user.setLoginId(userForm.getLoginId());
        user.setLoginPassword(userForm.getPassword());

        userService.join(user);
        return "redirect:/";
    }




}
