package project.gradproject.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class WaitingServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private WaitingService waitingService;

    @Test
//    @Commit
    public void findStoreWaitingList(){
        //given
        User user =new User();
        user.setName("이명규");
        userService.join(user);

        User user1 =new User();
        user1.setName("안진수");
        userService.join(user1);
        User user2 =new User();
        user2.setName("조해민");
        userService.join(user2);

        Store store = new Store();
        store.setName("누나홀닭");
        storeService.join(store);

        Store store2 = new Store();
        store2.setLoginId("qwe");
        store2.setLoginPassword("qwe");
        store2.setName("월순");
        store2.setTableCount(20);
        store2.setRestTableCount(20);
        store2.setAddress("망원동");
        store2.setStoreStatus(StoreStatus.CLOSED);
        storeService.join(store2);

        // when

        Long waitingId = waitingService.waiting(user.getId(), store.getId(), 2);
        Long waitingId1 = waitingService.waiting(user1.getId(), store.getId(), 3);
        Long waitingId2 = waitingService.waiting(user2.getId(), store.getId(), 4);
        Store one = storeService.findOne(store.getId());
        assertThat(one.getWaitingList().size()).isEqualTo(3);

        /*waitingService.enterWaiting(waitingId);
        assertThat(user.getWaiiting()).isEqualTo(null);


        Long id = waitingService.waiting(user1.getId(), store2.getId(), 3);
        assertThat(user1.getWaiting().getId()).isEqualTo(id);*/
    }
/*    @Test
    public void enter(){
        waitingService.enterWaiting();
    }*/


}