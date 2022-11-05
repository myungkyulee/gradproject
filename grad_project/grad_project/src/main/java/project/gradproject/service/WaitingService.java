package project.gradproject.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.waiting.StoreWaitingDTO;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;
import project.gradproject.repository.StoreRepository;
import project.gradproject.repository.UserRepository;
import project.gradproject.repository.WaitingRepository;

import java.util.ArrayList;
import java.util.Collections;
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

    public Waiting findWaiting(Long waitingId){
        return waitingRepository.findById(waitingId);
    }

    public List<StoreWaitingDTO> findStoreCurrentWaitingList(Long storeId){
        System.out.println("service Ok");
        List<Waiting> waitingList = waitingRepository.findStoreCurrentWaitingList(storeId);

        ArrayList<StoreWaitingDTO> newWaitingList = new ArrayList<>();
        for (int i=0;i<waitingList.size();i++){
            Waiting waiting = waitingList.get(i);
            StoreWaitingDTO storeWaitingDTO = new StoreWaitingDTO();
            storeWaitingDTO.setId(waiting.getId());
            storeWaitingDTO.setStatus(waiting.getStatus());
            storeWaitingDTO.setPeopleNum(waiting.getPeopleNum());
            storeWaitingDTO.setUsername(waiting.getUser().getName());
            storeWaitingDTO.setNum(i+1);
            String s = waiting.getCreatedAt().toString();
            String[] strArr = s.split(" ");
            String[] date = strArr[0].split("-");
            String[] time = strArr[1].split(":");
            String result = date[1] + "-" + date[2] + " " + time[0] + ":" + time[1];
            storeWaitingDTO.setTime(result);
            newWaitingList.add(storeWaitingDTO);
        }

        return newWaitingList;
    }
    public List<Waiting> getStoreCurrentWaitingList(Long storeId){
        System.out.println("service Ok");
        return waitingRepository.findStoreCurrentWaitingList(storeId);

    }
    public List<StoreWaitingDTO> findStoreLastWaitingList(Long storeId){

        List<Waiting> waitingList = waitingRepository.findStoreLastWaitingList(storeId);
        ArrayList<StoreWaitingDTO> newWaitingList = new ArrayList<>();
        for (int i=0;i<waitingList.size();i++){
            Waiting waiting = waitingList.get(i);
            StoreWaitingDTO storeWaitingDTO = new StoreWaitingDTO();
            storeWaitingDTO.setId(waiting.getId());
            storeWaitingDTO.setStatus(waiting.getStatus());
            storeWaitingDTO.setPeopleNum(waiting.getPeopleNum());
            storeWaitingDTO.setUsername(waiting.getUser().getName());
            storeWaitingDTO.setNum(i+1);
            String s = waiting.getCreatedAt().toString();
            String[] strArr = s.split(" ");
            String[] date = strArr[0].split("-");
            String[] time = strArr[1].split(":");
            String result = date[1] + "-" + date[2] + " " + time[0] + ":" + time[1];
            storeWaitingDTO.setTime(result);
            newWaitingList.add(storeWaitingDTO);
        }

        return newWaitingList;
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
        store.setRestTableCount(count - 1);
        User user = waiting.getUser();
        waiting.enter();
    }


}
