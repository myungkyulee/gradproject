package project.gradproject.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.waiting.WaitingStatus;
import project.gradproject.service.StoreService;
import project.gradproject.service.WaitingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {


    private final StoreService storeService;
    private final WaitingService waitingService;

   /* @GetMapping("/{storeId}")
    public String storeHome(@PathVariable Long storeId, Model model){
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
    }
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
    public String storeHome(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session==null) return "redirect:/";

        Long loginStoreId = (Long) session.getAttribute("loginStoreId");
        if(loginStoreId==null) return "redirect:/";

        Store store = storeService.findOne(loginStoreId);
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
                             @RequestParam("restTable") int restTable){
        HttpSession session = request.getSession(false);
        if(session==null) return "redirect:/";
        Long storeId = (Long) session.getAttribute("loginStoreId");
        if(storeId==null) return "redirect:/";
        storeService.updateStore(storeId, restTable);

        return "redirect:/store";
    }

    @PostMapping("/{waitingId}/entrance")
    public String entrance(@PathVariable Long waitingId){
        waitingService.enterWaiting(waitingId);
        return "redirect:/store";
    }



}
