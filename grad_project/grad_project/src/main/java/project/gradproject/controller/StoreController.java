package project.gradproject.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.gradproject.domain.LocationDTO;
import project.gradproject.domain.StoreJoinForm;
import project.gradproject.domain.store.PosStoreReq;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.waiting.StoreWaitingDTO;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.waiting.WaitingStatus;
import project.gradproject.service.StoreService;
import project.gradproject.service.WaitingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final WaitingService waitingService;

   /*
    @GetMapping
    public String storeHome1(@SessionAttribute(name="loginStoreId", required=true) Long storeId, Model model) {


        if(storeId==null){
            return "home";
        }
        Store store = storeService.findOne(storeId);
        List<Waiting> waitings = store.getWaitingList();
        List<Waiting> waitingList = new ArrayList<>();
        for(int i=0;i<waitings.size();i++){
            Waiting waiting = waitings.get(i);
            if(waiting.getStatus()==WaitingStatus.WAIT){
                waitingList.add(waiting);
            }
        }
        store.setWaitingList(waitingList);
        model.addAttribute("store", store);
        return "store/storeHome";
    }*/


    @GetMapping
    public String storeHome(){
        return "redirect:/store/currentWaiting";
    }

    /*@PostMapping
    public String storeHomeInput(@PathVariable Long storeId,
                             @RequestParam("restTable") int restTable){
        System.out.println("tableCount OK");
        storeService.updateStore(storeId, restTable);

        return "redirect:/store/{storeId}";
    }*/
    @PostMapping
    public String storeHomeInput(HttpServletRequest request,
                                 @ModelAttribute("restTable") Integer restTable,
                                 BindingResult result){
        HttpSession session = request.getSession(false);
        if(session==null) return "redirect:/";
        Long storeId = (Long) session.getAttribute("loginStoreId");
        if(storeId==null) return "redirect:/";
        storeService.updateStore(storeId, restTable);

        return "redirect:/store";
    }

    @GetMapping("/currentWaiting")
    public String getCurrentWaitingList(HttpServletRequest request,
                                 Model model){
        System.out.println("controller Ok");
        HttpSession session = request.getSession(false);
        if(session==null) return "redirect:/";
        Long storeId = (Long) session.getAttribute("loginStoreId");
        if(storeId==null) return "redirect:/";

        List<StoreWaitingDTO> storeCurrentWaitingList = waitingService.findStoreCurrentWaitingList(storeId);
        Store store = storeService.findOne(storeId);
        model.addAttribute("store", store);
        model.addAttribute("waitings", storeCurrentWaitingList);

        return "store/html/current-waiting";
    }
    @GetMapping("/lastWaiting")
    public String getLastWaitingList(HttpServletRequest request,
                                 Model model){
        HttpSession session = request.getSession(false);
        if(session==null) return "redirect:/";
        Long storeId = (Long) session.getAttribute("loginStoreId");
        if(storeId==null) return "redirect:/";

        List<StoreWaitingDTO> storeCurrentWaitingList = waitingService.findStoreLastWaitingList(storeId);
        Store store = storeService.findOne(storeId);
        model.addAttribute("store", store);
        model.addAttribute("waitings", storeCurrentWaitingList);

        return "store/html/last-waiting";
    }
    @GetMapping("/info")
    public String getStoreInfo(HttpServletRequest request,
                                 Model model){
        HttpSession session = request.getSession(false);
        if(session==null) return "redirect:/";
        Long storeId = (Long) session.getAttribute("loginStoreId");
        if(storeId==null) return "redirect:/";
        Store store = storeService.findOne(storeId);
        model.addAttribute("store", store);

        return "store/html/store-info";
    }
    @PostMapping("/info")
    public String updateStoreInfo(HttpServletRequest request,
                               @ModelAttribute PosStoreReq posStoreReq,
                               Model model){
        System.out.println("updateStoreInfo");
        System.out.println("updateStoreInfo");
        System.out.println("updateStoreInfo");
        System.out.println("updateStoreInfo");
        System.out.println("updateStoreInfo");
        System.out.println("updateStoreInfo");
        System.out.println("updateStoreInfo");
        System.out.println("updateStoreInfo");
        HttpSession session = request.getSession(false);
        if(session==null) return "redirect:/";
        Long storeId = (Long) session.getAttribute("loginStoreId");
        if(storeId==null) return "redirect:/";
        Store store = storeService.updateStoreInfo(storeId, posStoreReq);
        model.addAttribute("store", store);

        return "store/html/store-info";
    }
    @GetMapping("/info/location")
    public String getLocationForm(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session==null) return "redirect:/";
        Long storeId = (Long) session.getAttribute("loginStoreId");
        if(storeId==null) return "redirect:/";
        Store store = storeService.findOne(storeId);
        model.addAttribute("user", store);

        return "user/location";
    }
    @PostMapping("/info/location")
    public String updateLocation(@ModelAttribute LocationDTO location, HttpServletRequest request, Model model){
        System.out.println("updateLocation");
        System.out.println("updateLocation");
        System.out.println("updateLocation");
        System.out.println("updateLocation");
        System.out.println("updateLocation");
        System.out.println("updateLocation");
        System.out.println(location.getLocation());
        System.out.println(location.getX());
        System.out.println(location.getY());

        HttpSession session = request.getSession(false);
        if(session==null) return "redirect:/";
        Long storeId = (Long) session.getAttribute("loginStoreId");
        if(storeId==null) return "redirect:/";

        Store store = storeService.updateStoreLocation(storeId, location);
        model.addAttribute("store", store);

        return "redirect:/store/info";
    }

    @PostMapping("/{waitingId}/entrance")
    public String entrance(@PathVariable Long waitingId){
        waitingService.enterWaiting(waitingId);
        return "redirect:/store/currentWaiting";
    }



}
