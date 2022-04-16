package project.gradproject.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;
import project.gradproject.repository.StoreRepository;
import project.gradproject.repository.UserRepository;
import project.gradproject.repository.WaitingRepository;

import java.util.List;

@Service
@Transactional(readOnly =true)
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Long waiting(Long userId, Long storeId, int peopleNum){
        User user = userRepository.findById(userId);
        Store store = storeRepository.findById(storeId);

        Waiting waiting = Waiting.createWaiting(user, store,peopleNum);
        waitingRepository.save(waiting);
        return waiting.getId();
    }

    public List<Waiting> findWaitings(Long storeId){

        return waitingRepository.findStoreWaitingList(storeId);
    }
    /*  public List<Waiting> findWaitings2(Long storeId){
          Store store = storeRepository.findById(storeId);
          return store.getWatingList();
      }*/

    @Transactional
    public void enterWaiting(Long waitingId) {
        Waiting waiting = waitingRepository.findById(waitingId);
        Store store = waiting.getStore();
        int count = store.getRestTableCount();
        store.setRestTableCount(count + 1);

        waiting.enter();
    }


}
