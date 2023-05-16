package project.gradproject.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.Keyword;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.domain.user.User;
import project.gradproject.service.store.StoreService;
import project.gradproject.service.user.UserService;

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
        // 테스트 가게정보 저장
        Store store1 = new Store();
        store1.setEmail("dlaudrb");
        store1.setPassword("asd123!");
        store1.setName("GAMARO");
        store1.setTableCount(10);
        store1.setRestTableCount(10);
        store1.setLocationX(126.9313759);
        store1.setLocationY(37.5483009);
        store1.setLocationName("서울특별시 마포구 창전동 444 서강쌍용예가");
        store1.setInfo("대흥역 가마로강정인데 가마로는 원래 맛있어");
        store1.setStoreStatus(StoreStatus.CLOSED);
        store1.setImagePath("https://dnvefa72aowie.cloudfront.net/business/bizPlatform/profile/23534831/1623770243959/e6c6c2482e881b23ba471d27a90eaaa7cdb5fab9615bf54e31b419d49e25873c.jpeg?q=82&s=640x640&t=crop");

        Store store2 = new Store();
        store2.setEmail("qwe");
        store2.setPassword("qwe");
        store2.setName("월순");
        store2.setTableCount(20);
        store2.setRestTableCount(20);
        store2.setLocationX(126.9290697);
        store2.setLocationY(37.5672271);
        store2.setLocationName("서울시 서대문구 연희맛로 17-14 1층");

        store2.setInfo("동태찜 전문점인데 아구찜이 너무 맛있어");
        store2.setStoreStatus(StoreStatus.CLOSED);
        store2.setImagePath("https://t1.daumcdn.net/cfile/tistory/14469E4E51788ECE1B");

        Store store3 = new Store();
        store3.setEmail("asd");
        store3.setPassword("asd");
        store3.setName("간판없는가게");
        store3.setTableCount(20);
        store3.setRestTableCount(20);
        store3.setLocationX(126.9902802);
        store3.setLocationY(37.5738066);
        store3.setLocationName("서울특별시 종로구 익선동 166-18");

        store3.setInfo("파스타 맛있어 진심");
        store3.setStoreStatus(StoreStatus.CLOSED);
        store3.setImagePath("https://img.siksinhot.com/place/1536302691681018.jpg?w=307&h=300&c=Y");

        //테스트 유저정보 저장
        User user1=new User();
        user1.setName("이명규");
        user1.setEmail("test");
        user1.setPassword("test");
        user1.setLocationName("위치를 설정해주세요");

        User user2=new User();
        user2.setName("변상욱");
        user2.setEmail("test2");
        user2.setPassword("test2");
        user1.setLocationName("위치를 설정해주세요");
        User user3=new User();
        user3.setName("안진수");
        user3.setEmail("test3");
        user3.setPassword("test3");
        user1.setLocationName("위치를 설정해주세요");
        // 키워드 저장
        Keyword keyword1 = new Keyword();
        keyword1.setName("망원동");

        Keyword keyword2 = new Keyword();
        keyword2.setName("닭강정");

        Keyword keyword3 = new Keyword();
        keyword3.setName("아구찜");

        Keyword keyword4 = new Keyword();
        keyword4.setName("대흥동");

        Keyword keyword5 = new Keyword();
        keyword5.setName("마포");

        Keyword keyword6 = new Keyword();
        keyword6.setName("서울");


        keywordService.saveKeyword(keyword1);
        keywordService.saveKeyword(keyword2);
        keywordService.saveKeyword(keyword3);
        keywordService.saveKeyword(keyword4);
        keywordService.saveKeyword(keyword5);
        keywordService.saveKeyword(keyword6);





        userService.join(user1);
        userService.join(user2);
        userService.join(user3);

        storeService.join(store1);
        storeService.join(store2);
        storeService.join(store3);

        waitingService.waiting(user1.getId(),store1.getId(),2);
        waitingService.waiting(user1.getId(),store2.getId(),1);
        Long waiting = waitingService.waiting(user1.getId(), store3.getId(), 4);
        waitingService.enterWaiting(waiting);
        waitingService.waiting(user2.getId(),store1.getId(),4);
        waitingService.waiting(user3.getId(),store2.getId(),4);



        storeService.addKeyword(store1.getId(), keyword4.getId());
        storeService.addKeyword(store1.getId(), keyword2.getId());
        storeService.addKeyword(store2.getId(), keyword1.getId());
        storeService.addKeyword(store2.getId(), keyword3.getId());
        storeService.addKeyword(store2.getId(), keyword5.getId());
        storeService.addKeyword(store1.getId(), keyword5.getId());
        storeService.addKeyword(store2.getId(), keyword6.getId());
        storeService.addKeyword(store1.getId(), keyword6.getId());
        storeService.addKeyword(store3.getId(), keyword6.getId());
        storeService.addKeyword(store3.getId(), keyword6.getId());


    }


}
