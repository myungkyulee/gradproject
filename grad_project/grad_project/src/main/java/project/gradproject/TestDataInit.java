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
        store1.setAddress("서울시 마포구 대흥동 대흥역 1분거리");
        store1.setInfo("대흥역 가마로강정인데 가마로는 원래 맛있어");
        store1.setStoreStatus(StoreStatus.CLOSED);
        store1.setImagePath("https://dnvefa72aowie.cloudfront.net/business/bizPlatform/profile/23534831/1623770243959/e6c6c2482e881b23ba471d27a90eaaa7cdb5fab9615bf54e31b419d49e25873c.jpeg?q=82&s=640x640&t=crop");

        Store store2 = new Store();
        store2.setLoginId("qwe");
        store2.setLoginPassword("qwe");
        store2.setName("월순");
        store2.setTableCount(20);
        store2.setRestTableCount(20);
        store2.setAddress("망원동 월순");
        store2.setInfo("동태찜 전문점인데 아구찜이 너무 맛있어");
        store2.setStoreStatus(StoreStatus.CLOSED);
        store2.setImagePath("https://t1.daumcdn.net/cfile/tistory/14469E4E51788ECE1B");

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
