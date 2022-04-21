package project.gradproject;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.domain.user.User;
import project.gradproject.service.StoreService;
import project.gradproject.service.UserService;
import project.gradproject.service.WaitingService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final StoreService storeService;
    private final UserService userService;
    private final WaitingService waitingService;

    @PostConstruct
    public void init(){
        // 테스트 가게정보 저장
        Store store1 = new Store();
        store1.setLoginId("dlaudrb");
        store1.setLoginPassword("asd123!");
        store1.setName("GAMARO");
        store1.setTableCount(10);
        store1.setRestTableCount(10);
        store1.setAddress("대흥동");
        store1.setStoreStatus(StoreStatus.CLOSED);

        Store store2 = new Store();
        store2.setLoginId("qwe");
        store2.setLoginPassword("qwe");
        store2.setName("월순");
        store2.setTableCount(20);
        store2.setRestTableCount(20);
        store2.setAddress("망원동");
        store2.setStoreStatus(StoreStatus.CLOSED);

        //테스트 유저정보 저장
        User user1=new User();
        user1.setName("이명규");
        user1.setLoginId("test");
        user1.setLoginPassword("test");

        User user2=new User();
        user2.setName("변상욱");
        user2.setLoginId("testUser2");
        user2.setLoginPassword("testUser2!");

        User user3=new User();
        user3.setName("안진수");
        user3.setLoginId("testUser3");
        user3.setLoginPassword("testUser3!");


        userService.join(user1);
        userService.join(user2);
        userService.join(user3);

        storeService.join(store1);
        storeService.join(store2);

        waitingService.waiting(user1.getId(),store1.getId(),2);
        waitingService.waiting(user2.getId(),store1.getId(),4);
        waitingService.waiting(user3.getId(),store2.getId(),4);
    }


}
