package project.gradproject.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.gradproject.domain.Favorite;
import project.gradproject.domain.StoreDTO;
import project.gradproject.domain.store.Address;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.domain.user.User;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.waiting.WaitingStatus;
import project.gradproject.service.KeywordService;
import project.gradproject.service.StoreService;
import project.gradproject.service.UserService;
import project.gradproject.service.WaitingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final StoreService storeService;
    private final UserService userService;
    private final WaitingService waitingService;
    private final KeywordService keywordService;

    @GetMapping
    public String userHome(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) return "redirect:/";
        Long loginUserId = (Long) session.getAttribute("loginUserId");
        if (loginUserId == null) return "redirect:/";

        User user = userService.findOne(loginUserId);

        List<Store> storeList = storeService.findStores();

        List<StoreDTO> stores = storeService.setStoreDTO(storeList);

        model.addAttribute("stores", stores);
        model.addAttribute("user", user);
        return "user/userHome";
    }

    @GetMapping("/{storeName}")
    public String storeWindow(@PathVariable("storeName") String name,
                              HttpServletRequest request,
                              Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) return "redirect:/";
        Long loginUserId = (Long) session.getAttribute("loginUserId");
        if (loginUserId == null) return "redirect:/";
        User user = userService.findOne(loginUserId);
        Store store = storeService.findOneByName(name);
        List<Favorite> favorites = user.getFavorites();
        Boolean check = false;
        for (Favorite f : favorites) {
            if (f.getStore() == store) {
                check = true;
                break;
            }
        }


        StoreDTO storeDTO = storeService.setStoreDTO(store);


        model.addAttribute("store", storeDTO);
        model.addAttribute("check", check);
        return "user/storeForm";
    }


    @PostMapping("/{storeName}")
    public String waiting(@PathVariable("storeName") String name,
                          @RequestParam("peopleNum") Integer peopleNum,
                          HttpServletRequest request,
                          Model model) {

        System.out.println("name = " + name);
        System.out.println("peopleNum = " + peopleNum);

        HttpSession session = request.getSession(false);
        if (session == null) return "redirect:/";
        Long loginUserId = (Long) session.getAttribute("loginUserId");
        if (loginUserId == null) return "redirect:/";

        Store store = storeService.findOneByName(name);
        User user = userService.findOne(loginUserId);


        List<Waiting> waitingOnList = getWaitingOnList(user);
        int count = waitingOnList.size();


        Waiting check = waitingOnList.stream()
                .filter(waiting -> waiting.getStore() == store)
                .findFirst().orElse(null);

        System.out.println("count = " + count);

        // ???????????? 3?????? ?????? or ?????? ??? ???????????? ???????????? ????????????
        if (count + 1 > 3 || check != null) {

            System.out.println("??????????????? ?????? ");

            // ????????? ???????????? ?????? ?????????????????? ?????????. ????????? ?????? ?????????????????? ????????? .
            model.addAttribute("store", store);
            return "redirect:/user/{storeName}";
        }
        System.out.println("????????? ?????? ");

        Long waiting = waitingService.waiting(user.getId(), store.getId(), peopleNum);
        return "redirect:/user";
    }


    @GetMapping("/search")
    public String search(@ModelAttribute("keyword") String keyword, Model model) {

        if (keyword.equals("")) return "user/searchForm";
        List<String> keywords = keywordService.splitKeyword(keyword);
        List<Store> storeList = storeService.findKeywordStores(keywords);

        List<StoreDTO> stores = storeService.setStoreDTO(storeList);
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


    //ajax ??????????????? ????????? ????????? ????????? ????????????
    @GetMapping("/favorite/{storeName}")
    public String favorite(@PathVariable("storeName") String name,
                           HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session == null) return "redirect:/";
        Long userId = (Long) session.getAttribute("loginUserId");
        if (userId == null) return "redirect:/";
        Store store = storeService.findOneByName(name);
        User user = userService.findOne(userId);
        List<Favorite> favorites = user.getFavorites();
        boolean check = false;
        for (Favorite f : favorites) {
            if (f.getStore().getName().equals(name)) {
                check = true;
                userService.removeFavorite(user, f);
                break;
            }
        }
        if (check != true) {
            userService.favorite(store.getId(), user.getId());
        }

        return "redirect:/user/{storeName}";
    }


    @GetMapping("/favorite")
    public String favoriteList(HttpServletRequest request,
                               Model model) {
        HttpSession session = request.getSession();
        if (session == null) return "redirect:/";
        Long userId = (Long) session.getAttribute("loginUserId");
        if (userId == null) return "redirect:/";

        User user = userService.findOne(userId);

        List<StoreDTO> stores = new ArrayList<>();

        List<Favorite> favorites = user.getFavorites();
        for(Favorite favorite:favorites){
            StoreDTO storeDTO = storeService.setStoreDTO(favorite.getStore());
            stores.add(storeDTO);
        }


        model.addAttribute("stores",stores);
        return "user/favorite";
    }
}
