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
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.service.store.StoreService;
import project.gradproject.service.user.UserService;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinController {

    private final StoreService storeService;
    private final UserService userService;

    @GetMapping
    public String addForm() {
        return "join/addMemberForm";
    }

    @GetMapping("/store")
    public String addStoreForm(@ModelAttribute("member") StoreJoinForm store) {
        return "join/addStoreForm";
    }

    @PostMapping("/store")
    public String saveStore(@Validated @ModelAttribute("member") StoreJoinForm storeForm, BindingResult result) {
        if (result.hasErrors()) {
            return "join/addStoreForm";
        }

        String ad = storeForm.getLocationName();


        Store store = new Store();

        store.setLocationName(ad);
        store.setLocationX(storeForm.getLocationX());
        store.setLocationY(storeForm.getLocationY());
        store.setName(storeForm.getName());
        store.setPassword(storeForm.getPassword());
        store.setTableCount(storeForm.getTableCount());
        store.setStoreStatus(StoreStatus.CLOSED);
        store.setRestTableCount(store.getTableCount());
        store.setInfo(storeForm.getInfo());

        storeService.join(store);


        return "redirect:/";
    }

    @GetMapping("/store/location")
    public String storeLocation(@ModelAttribute("store") StoreJoinForm storeJoinForm,
                                Model model) {

        if (storeJoinForm.getLocationName() == null) {
            storeJoinForm.setLocationName("위치를 설정해주세요");
        }
        model.addAttribute("user", storeJoinForm);
        return "user/location";
    }


    @PostMapping("/store/location")
    public String locationUpdate(@RequestParam("location") String location,
                                 @RequestParam("x") Double x,
                                 @RequestParam("y") Double y,
                                 Model model) {
        StoreJoinForm storeJoinForm = new StoreJoinForm();
        storeJoinForm.setLocationName(location);
        storeJoinForm.setLocationX(x);
        storeJoinForm.setLocationY(y);
        model.addAttribute("member", storeJoinForm);
        return "join/addStoreForm";
    }


    @GetMapping("/user")
    public String addUserForm(@ModelAttribute("member") UserJoinForm user) {
        return "join/addUserForm";
    }

    @PostMapping("/user")
    public String createUser(@ModelAttribute("member") UserJoinForm userForm,
                             BindingResult result) {

        if (result.hasErrors()) {
            return "join/addUserForm";
        }

        userService.join(userForm);
        return "redirect:/login";
    }
}
