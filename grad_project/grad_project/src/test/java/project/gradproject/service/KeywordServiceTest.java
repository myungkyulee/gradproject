package project.gradproject.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.Keyword;
import project.gradproject.domain.store.Address;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreKeyword;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.domain.user.User;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class KeywordServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private WaitingService waitingService;
    @Autowired
    private KeywordService keywordService;

    @Test
    public void a(){
        Store store1 = new Store();
        store1.setLoginId("dlaudrb");
        store1.setLoginPassword("asd123!");
        store1.setName("GAMARO");
        store1.setTableCount(10);
        store1.setRestTableCount(10);
        Address address = storeService.splitAddress("서울시 마포구 대흥동 대흥역 1분거리");
        store1.setAddress(address);
        store1.setInfo("대흥역 가마로강정인데 가마로는 원래 맛있어");
        store1.setStoreStatus(StoreStatus.CLOSED);
        store1.setImagePath("https://dnvefa72aowie.cloudfront.net/business/bizPlatform/profile/23534831/1623770243959/e6c6c2482e881b23ba471d27a90eaaa7cdb5fab9615bf54e31b419d49e25873c.jpeg?q=82&s=640x640&t=crop");

        Store store2 = new Store();
        store2.setLoginId("qwe");
        store2.setLoginPassword("qwe");
        store2.setName("월순");
        store2.setTableCount(20);
        store2.setRestTableCount(20);
        Address address1 = storeService.splitAddress("서울시 마포구 망원동 맛있어요");
        store2.setAddress(address1);
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

        // 키워드 저장
        Keyword keyword1 = new Keyword();
        keyword1.setName("망원동");

        Keyword keyword2 = new Keyword();
        keyword2.setName("닭강정");

        Keyword keyword3 = new Keyword();
        keyword3.setName("아구찜");

        Keyword keyword4 = new Keyword();
        keyword4.setName("대흥동");

        keywordService.saveKeyword(keyword1);
        keywordService.saveKeyword(keyword2);
        keywordService.saveKeyword(keyword3);
        keywordService.saveKeyword(keyword4);





        userService.join(user1);
        userService.join(user2);
        userService.join(user3);

        storeService.join(store1);
        storeService.join(store2);

        waitingService.waiting(user1.getId(),store1.getId(),2);
        waitingService.waiting(user2.getId(),store1.getId(),4);
        waitingService.waiting(user3.getId(),store2.getId(),4);



        storeService.addKeyword(store1.getId(), keyword4.getId());
        storeService.addKeyword(store1.getId(), keyword2.getId());
        storeService.addKeyword(store2.getId(), keyword1.getId());
        storeService.addKeyword(store2.getId(), keyword3.getId());

        List<User> users = userService.findUsers();
        System.out.println(users.size());

        List<Store> stores = storeService.findStores();
        System.out.println(stores.size());
        for(Store s:stores){
            System.out.println(s.getName());
        }
        /*String keyword="아구찜";
        List<Store> stores = storeService.findKeywordStores(keyword);
        System.out.println(stores.size());
        for(Store s:stores){
            System.out.println(s.getName()+" : "+s.getName());
        }*/


    }


}
