package project.gradproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly =true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    @Transactional
    public Long join(Store store){
        // 중복회원 검증은 나중에
        /*List<Store> stores = storeRepository.findByName(store.getName());
        if(!stores.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원");
        }*/

        storeRepository.save(store);
        return store.getId();
    }

    public List<Store> findStores(){
        return storeRepository.findAll();
    }

    public Store findOne(Long storeId){
        return storeRepository.findById(storeId);
    }

    public Store login(String loginId, String password) {
        /*Optional<Store> byLoginId = storeRepository.findByLoginId(loginId);

        Store store = byLoginId.get();


        if (store.getLoginPassword().equals(password)) {
            return store;
        }
        else{
            return null;
        }*/

        return storeRepository.findByLoginId(loginId)
                .filter(m -> m.getLoginPassword().equals(password))
                .orElse(null);
    }

    @Transactional
    public void updateStore(Long id, int restTable) {
        Store store = storeRepository.findById(id);
        if(store.getTableCount()<restTable) return;
        else if(restTable<0) return;
        store.setRestTableCount(restTable);
    }

    @Transactional
    public void openStore(Long id) {
        Store store = storeRepository.findById(id);

        store.setStoreStatus(StoreStatus.OPEN);

        int waitingCount = store.getWaitingList().size();
        int tableCount = store.getTableCount();
        store.setRestTableCount(tableCount-waitingCount);
    }
    @Transactional
    public void closeStore(Long id) {
        Store store = storeRepository.findById(id);

        store.setStoreStatus(StoreStatus.CLOSED);
        store.setRestTableCount(0);
    }
}
