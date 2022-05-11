package project.gradproject.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.domain.user.User;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.waiting.WaitingStatus;
import project.gradproject.service.StoreService;
import project.gradproject.service.UserService;
import project.gradproject.service.WaitingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final StoreService storeService;
    private final UserService userService;
    private final WaitingService waitingService;

    @GetMapping
    public String userHome(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);

        if(session==null) return "redirect:/";
        Long loginUserId = (Long) session.getAttribute("loginUserId");
        if(loginUserId==null) return "redirect:/";

        User user = userService.findOne(loginUserId);

        List<Store> storeList = storeService.findStores();

        /*List<Store> stores = new ArrayList<>();

        for (Store store : storeList) {
            if(store.getStoreStatus()==StoreStatus.OPEN) stores.add(store);
        }
*/

        model.addAttribute("stores",storeList);
        model.addAttribute("user",user);
        return "user/userHome";
    }

    @GetMapping("/{storeName}")
    public String storeWindow(@PathVariable("storeName") String name,
                              HttpServletRequest request,
                              Model model) {
        Store store = storeService.findOneByName(name);
        model.addAttribute("store", store);
        return "user/storeForm";
    }

    @PostMapping("/{storeName}")
    public String waiting(@PathVariable("storeName") String name,
                          @RequestParam("peopleNum") Integer peopleNum,
                          HttpServletRequest request,
                          Model model){

        System.out.println("name = " + name);
        System.out.println("peopleNum = " + peopleNum);

        HttpSession session = request.getSession(false);
        if(session==null) return "redirect:/";
        Long loginUserId = (Long) session.getAttribute("loginUserId");
        if(loginUserId==null) return "redirect:/";

        Store store = storeService.findOneByName(name);
        User user = userService.findOne(loginUserId);


        List<Waiting> waitingOnList = getWaitingOnList(user);
        int count = waitingOnList.size();


        Waiting check = waitingOnList.stream()
                .filter(waiting -> waiting.getStore() == store)
                .findFirst().orElse(null);

        System.out.println("count = " + count);

        // 웨이팅은 3개만 가능 or 지금 이 매장에서 웨이팅이 없어야함
        if (count + 1 > 3 || check != null ) {

            System.out.println("웨이팅하면 안됨 ");

            // 에러를 만들어서 다시 이전화면으로 보낸다. 아니면 따로 에러페이지를 만든다 .
            model.addAttribute("store", store);
            return "redirect:/user/{storeName}";
        }
        System.out.println("웨이팅 가능 ");

        Long waiting = waitingService.waiting(user.getId(), store.getId(), peopleNum);
        return "redirect:/user";
    }


    @GetMapping("/search")
    public String searchForm(){
        return "user/searchForm";
    }

    @PostMapping("/search")
    public String search(@RequestParam String keyword, Model model){
        System.out.println("ok");
        List<Store> stores = storeService.findKeywordStores(keyword);

        model.addAttribute("stores", stores);
        return "user/search";
    }

    private List<Waiting> getWaitingOnList(User user) {

        List<Waiting> waitingList = user.getWaitingList();
        List<Waiting> waitings = new ArrayList<>();
        for (Waiting waiting : waitingList) {
            if (waiting.getStatus() == WaitingStatus.WAIT) waitings.add(waiting);
        }
        return waitings;
    }
}
