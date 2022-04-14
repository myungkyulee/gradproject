package project.gradproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        /* userService.join(user);*/
        Store store = new Store();
        store.setName("누나홀닭");
        storeService.join(store);
/*        Store store1 = new Store();
        store1.setName("누나홀닭");
        storeService.join(store);
        Store store2 = new Store();
        store2.setName("누나홀닭");
        storeService.join(store);
        System.out.println("ok");*/
        // when

        Long waitingId = waitingService.waiting(user.getId(), store.getId(), 2);
        Long waitingId1 = waitingService.waiting(user1.getId(), store.getId(), 3);
        Long waitingId2 = waitingService.waiting(user2.getId(), store.getId(), 4);
        Store one = storeService.findOne(store.getId());
        assertThat(one.getWatingList().size()).isEqualTo(3);



    }
}