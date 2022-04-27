package project.gradproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.gradproject.domain.StoreJoinForm;
import project.gradproject.domain.UserJoinForm;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.domain.user.User;
import project.gradproject.service.StoreService;
import project.gradproject.service.UserService;


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
        Store store = new Store();
        store.setName(storeForm.getName());
        store.setLoginId(storeForm.getLoginId());
        store.setLoginPassword(storeForm.getPassword());
        store.setAddress(storeForm.getAddress());
        store.setTableCount(storeForm.getTableCount());
        store.setStoreStatus(StoreStatus.CLOSED);
        store.setRestTableCount(store.getTableCount());
        store.setInfo(storeForm.getInfo());

        storeService.join(store);
        return "redirect:/";
    }


    @GetMapping("/addUser")
    public String addUserForm(@ModelAttribute("member") UserJoinForm user){
        return "join/addUserForm";
    }
    @PostMapping("/addUser")
    public String saveUser(@Validated @ModelAttribute("member") UserJoinForm userForm, BindingResult result) {

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
