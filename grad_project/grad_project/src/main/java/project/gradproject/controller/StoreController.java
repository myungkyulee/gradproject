package project.gradproject.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.gradproject.annotation.CurrentStoreId;
import project.gradproject.annotation.LoginCheck;
import project.gradproject.domain.LocationDTO;
import project.gradproject.domain.MemberType;
import project.gradproject.domain.store.PosStoreReq;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.waiting.StoreWaitingDTO;
import project.gradproject.service.store.StoreLoginService;
import project.gradproject.service.store.StoreService;
import project.gradproject.service.WaitingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final WaitingService waitingService;
    private final StoreLoginService loginService;

    @GetMapping
    @LoginCheck(type = MemberType.STORE)
    public String storeHome() {
        return "redirect:/store/currentWaiting";
    }

    @PostMapping
    @LoginCheck(type = MemberType.STORE)
    public String storeHomeInput(@CurrentStoreId Long storeId,
                                 @ModelAttribute("restTable") Integer restTable,
                                 BindingResult result) {
        storeService.updateStore(storeId, restTable);

        return "redirect:/store";
    }

    @GetMapping("/currentWaiting")
    @LoginCheck(type = MemberType.STORE)
    public String getCurrentWaitingList(@CurrentStoreId Long storeId,
                                        Model model) {

        List<StoreWaitingDTO> storeCurrentWaitingList = waitingService.findStoreCurrentWaitingList(storeId);
        Store store = storeService.findOne(storeId);
        model.addAttribute("store", store);
        model.addAttribute("waitings", storeCurrentWaitingList);

        return "store/html/current-waiting";
    }

    @GetMapping("/lastWaiting")
    @LoginCheck(type = MemberType.STORE)
    public String getLastWaitingList(@CurrentStoreId Long storeId,
                                     Model model) {

        List<StoreWaitingDTO> storeCurrentWaitingList = waitingService.findStoreLastWaitingList(storeId);
        Store store = storeService.findOne(storeId);
        model.addAttribute("store", store);
        model.addAttribute("waitings", storeCurrentWaitingList);

        return "store/html/last-waiting";
    }

    @GetMapping("/info")
    @LoginCheck(type = MemberType.STORE)
    public String getStoreInfo(@CurrentStoreId Long storeId,
                               Model model) {
        Store store = storeService.findOne(storeId);
        model.addAttribute("store", store);

        return "store/html/store-info";
    }

    @PostMapping("/info")
    @LoginCheck(type = MemberType.STORE)
    public String updateStoreInfo(@CurrentStoreId Long storeId,
                                  @ModelAttribute PosStoreReq posStoreReq,
                                  Model model) {
        Store store = storeService.updateStoreInfo(storeId, posStoreReq);
        model.addAttribute("store", store);

        return "store/html/store-info";
    }

    @GetMapping("/info/location")
    @LoginCheck(type = MemberType.STORE)
    public String getLocationForm(@CurrentStoreId Long storeId, Model model) {
        Store store = storeService.findOne(storeId);
        model.addAttribute("user", store);

        return "user/location";
    }

    @PostMapping("/info/location")
    @LoginCheck(type = MemberType.STORE)
    public String updateLocation(@ModelAttribute LocationDTO location,
                                 @CurrentStoreId Long storeId, Model model) {

        Store store = storeService.updateStoreLocation(storeId, location);
        model.addAttribute("store", store);

        return "redirect:/store/info";
    }

    @PostMapping("/{waitingId}/entrance")
    @LoginCheck(type = MemberType.STORE)
    public String entrance(@PathVariable Long waitingId) {
        waitingService.enterWaiting(waitingId);
        return "redirect:/store/currentWaiting";
    }


}
