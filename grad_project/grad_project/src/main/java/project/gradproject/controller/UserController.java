package project.gradproject.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.gradproject.annotation.CurrentUserId;
import project.gradproject.annotation.LoginCheck;
import project.gradproject.domain.Favorite;
import project.gradproject.domain.MemberType;
import project.gradproject.dto.StoreDTO;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.domain.user.SearchWord;
import project.gradproject.domain.user.User;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.waiting.WaitingDTO;
import project.gradproject.domain.waiting.WaitingStatus;
import project.gradproject.service.KeywordService;
import project.gradproject.service.store.StoreService;
import project.gradproject.service.user.UserService;
import project.gradproject.service.WaitingService;

import java.sql.Timestamp;
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
    @LoginCheck(type = MemberType.USER)
    public String userHome(@CurrentUserId Long userId, Model model) {
        log.info("userHome method, userId = {}", userId);
        List<StoreDTO> stores;
        User user = userService.findOne(userId);

        if (user.getLocationX() == null || user.getLocationY() == null) {
            stores = storeService.findStoresPage(0).getStores();
        } else {
            stores = storeService.findStoresByDistance(user.getLocationX(), user.getLocationY(), 0);
        }

        model.addAttribute("stores", stores);
        model.addAttribute("user", user);
        return "user/newUserHome";
    }

    @GetMapping("/moreStores")
    public String getMoreStores(@CurrentUserId Long userId,
                            @RequestParam(defaultValue = "0") int page, Model model) {
        log.info("getStores called {}", page);
        log.info("userId = {}", userId);

        List<StoreDTO> stores;
        User user = userService.findOne(userId);

        if (user.getLocationX() == null || user.getLocationY() == null) {
            stores = storeService.findStoresPage(page).getStores();
        } else {
            stores = storeService.findStoresByDistance(user.getLocationX(), user.getLocationY(), page);
        }

        model.addAttribute("stores", stores);

        return "fragments/storecomponent";
    }

    @GetMapping("/{storeName}")
    @LoginCheck(type = MemberType.USER)
    public String storeWindow(@PathVariable("storeName") String name,
                              @CurrentUserId Long userId,
                              Model model) {

        User user = userService.findOne(userId);
        Store store = storeService.findOneByName(name);
        List<Favorite> favorites = user.getFavorites();
        boolean check = false;

        for (Favorite f : favorites) {
            if (f.getStore() == store) {
                check = true;
                break;
            }
        }
        boolean storeOpenCheck = store.getStoreStatus() == StoreStatus.OPEN;

        StoreDTO storeDTO = storeService.setStoreDTO(store);
        System.out.println(storeDTO.getLocationX());
        System.out.println(storeDTO.getLocationY());
        System.out.println(storeDTO.getLocationName());
        model.addAttribute("storeOpenCheck", storeOpenCheck);
        model.addAttribute("store", storeDTO);
        model.addAttribute("check", check);
        return "user/storeForm";
    }


    @PostMapping("/{storeName}")
    @LoginCheck(type = MemberType.USER)
    public String waiting(@PathVariable("storeName") String name,
                          @RequestParam("peopleNum") Integer peopleNum,
                          @CurrentUserId Long userId,
                          Model model) {

        System.out.println("name = " + name);
        System.out.println("peopleNum = " + peopleNum);


        Store store = storeService.findOneByName(name);
        User user = userService.findOne(userId);

        List<Waiting> waitingOnList = getWaitingOnList(user);
        int count = waitingOnList.size();

        Waiting check = waitingOnList.stream()
                .filter(waiting -> waiting.getStore() == store)
                .findFirst().orElse(null);

        System.out.println("count = " + count);

        // 웨이팅은 3개만 가능 or 지금 이 매장에서 웨이팅이 없어야함
        if (count + 1 > 3 || check != null) {

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
    @LoginCheck(type = MemberType.USER)
    public String search(@ModelAttribute("keyword") String keyword,
                         @CurrentUserId Long userId,
                         Model model) {

        if (keyword.equals("")) {
            User user = userService.findOne(userId);
            List<SearchWord> searchWords = user.getSearchWords();

            model.addAttribute("keywords", searchWords);
            return "user/newSearchForm";
        }

        userService.addSearchWord(userId, keyword);

        List<String> keywords = keywordService.splitKeyword(keyword);
        List<Store> storeList = storeService.findKeywordStores(keywords);

        List<StoreDTO> stores = storeService.setStoreDTO(storeList);
        model.addAttribute("stores", stores);
        return "user/newSearch";
    }

    private List<Waiting> getWaitingOnList(User user) {

        List<Waiting> waitingList = user.getWaitingList();
        List<Waiting> waitings = new ArrayList<>();
        for (Waiting waiting : waitingList) {
            if (waiting.getStatus() == WaitingStatus.WAIT) waitings.add(waiting);
        }
        return waitings;
    }


    //ajax 공부전까지 찜하기 처리는 이렇게 처리한다
    @GetMapping("/favorite/{storeName}")
    @LoginCheck(type = MemberType.USER)
    public String favorite(@PathVariable("storeName") String name,
                           @CurrentUserId Long userId, Model model) {

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
        if (!check) {
            userService.favorite(store.getId(), user.getId());
        }

        return "redirect:/user/{storeName}";
    }


    @GetMapping("/favorite")
    @LoginCheck(type = MemberType.USER)
    public String favoriteList(@CurrentUserId Long userId,
                               Model model) {

        User user = userService.findOne(userId);

        List<StoreDTO> stores = new ArrayList<>();

        List<Favorite> favorites = user.getFavorites();
        for(Favorite favorite:favorites){
            StoreDTO storeDTO = storeService.setStoreDTO(favorite.getStore());
            stores.add(storeDTO);
        }


        model.addAttribute("stores",stores);
        return "user/newFavorite";
    }

    @GetMapping("/info")
    @LoginCheck(type = MemberType.USER)
    public String info(@CurrentUserId Long userId, Model model){
        User user = userService.findOne(userId);

        model.addAttribute("user",user);

        return "user/newInfo";
    }

    @GetMapping("/currentWaitingList")
    @LoginCheck(type = MemberType.USER)
    public String currentList(@CurrentUserId Long userId, Model model){

        User user = userService.findOne(userId);

        List<Waiting> waitingList = user.getWaitingList();
        List<WaitingDTO> currentWaitingList = getCurrentWaitingList(waitingList);

        model.addAttribute("waitings",currentWaitingList);

        return "user/newCurrentWaitingList";
    }

    @GetMapping("/lastWaitingList")
    @LoginCheck(type = MemberType.USER)
    public String lastList(@CurrentUserId Long userId, Model model){

        User user = userService.findOne(userId);
        List<Waiting> waitingList = user.getWaitingList();
        List<WaitingDTO> enteredWaitingList = getEnteredWaitingList(waitingList);

        model.addAttribute("waitings",enteredWaitingList);

        return "user/newLastWaitingList";
    }

    @GetMapping("/location")
    @LoginCheck(type = MemberType.USER)
    public String location(@CurrentUserId Long userId, Model model){

        User user = userService.findOne(userId);
        model.addAttribute("user",user);
        return "user/location";
    }

    @PostMapping("/location")
    @LoginCheck(type = MemberType.USER)
    public String locationUpdate(@CurrentUserId Long userId,
                            @RequestParam("location") String location,
                                 @RequestParam("x") Double x,
                                 @RequestParam("y") Double y){


        User user = userService.findOne(userId);

        userService.setLocation(user, location,x,y);

        return "redirect:/user";
    }

    private List<WaitingDTO> getCurrentWaitingList(List<Waiting> waitingList){
        List<WaitingDTO> current = new ArrayList<>();

        for (Waiting w : waitingList) {
            if (w.getStatus() == WaitingStatus.WAIT) {
                WaitingDTO waitingDTO = new WaitingDTO();
                waitingDTO.setWaiting(w);
                List<Waiting> storeWaitingList = waitingService.getStoreCurrentWaitingList(w.getStore().getId());
                //Collections.sort(storeWaitingList);
                for (int num = 0; num < storeWaitingList.size(); num++) {
                    System.out.println(storeWaitingList.get(num).getUser().getName());
                    if (w.getId() == storeWaitingList.get(num).getId()) {
                        waitingDTO.setNum(num + 1);
                        break;
                    }
                }


                Timestamp createdAt = w.getCreatedAt();
                String s = createdAt.toString();
                ArrayList<String> list = new ArrayList<>();
                String str = "";
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (c == '-' || c == ' ' || c == ':') {
                        list.add(str);
                        str = "";
                    } else str += c;
                }
                String result = "";
                result += list.get(1) + "-" + list.get(2) + " " + list.get(3) + ":" + list.get(4);
                waitingDTO.setTime(result);
                current.add(waitingDTO);
            }
        }

        return current;
    }

    private List<WaitingDTO> getEnteredWaitingList(List<Waiting> waitingList){
        List<WaitingDTO> entered = new ArrayList<WaitingDTO>();

        for(Waiting w:waitingList){
            if(w.getStatus()!= WaitingStatus.WAIT) {
                WaitingDTO waitingDTO= new WaitingDTO();
                waitingDTO.setWaiting(w);
                Timestamp enteredAt = w.getEnteredAt();
                String s = enteredAt.toString();
                ArrayList<String> list = new ArrayList<>();
                String str="";
                for(int i=0;i<s.length();i++){
                    char c = s.charAt(i);
                    if(c=='-' || c==' ' || c==':') {
                        list.add(str);
                        str="";
                    }
                    else str+=c;
                }
                String result="";
                result+=list.get(1)+"-"+list.get(2)+" "+list.get(3)+":"+list.get(4);
                waitingDTO.setTime(result);
                entered.add(waitingDTO);
            }
        }

        return entered;
    }



}
