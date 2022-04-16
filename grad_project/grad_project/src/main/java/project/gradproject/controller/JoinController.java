package project.gradproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.gradproject.domain.StoreJoinForm;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.service.StoreService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class JoinController {

    private final StoreService storeService;

    @GetMapping
    public String addForm(){
        return "join/addMemberForm";
    }

    @GetMapping("/addStore")
    public String addStoreForm(@ModelAttribute("member") StoreJoinForm store){
        return "join/addStoreForm";
    }
    @PostMapping("/addStore")
    public String save(@Valid @ModelAttribute("member") StoreJoinForm storeForm, BindingResult
            result) {
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
        store.setTableCount(Integer.parseInt(storeForm.getTableCount()));
        store.setStoreStatus(StoreStatus.CLOSED);
        store.setRestTableCount(store.getTableCount());

        storeService.join(store);
        return "redirect:/";
    }
}
