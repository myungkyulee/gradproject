package project.gradproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.Favorite;
import project.gradproject.domain.Keyword;
import project.gradproject.domain.StoreDTO;
import project.gradproject.domain.store.Address;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreKeyword;
import project.gradproject.domain.store.StoreStatus;
import project.gradproject.repository.KeywordRepository;
import project.gradproject.repository.StoreKeywordRepository;
import project.gradproject.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly =true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final KeywordRepository keywordRepository;
    private final StoreKeywordRepository storeKeywordRepository;

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


    /*public List<Store> findStoresStatus(){
        return storeRepository.findByStatus(StoreStatus.OPEN);
    }
*/


    public Store findOne(Long storeId){
        return storeRepository.findById(storeId);
    }

    public Store findOneByName(String name){
        return storeRepository.findByName(name).get(0);
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
    public void updateStore(Long id, Integer restTable) {
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

    @Transactional
    public void addKeyword(Long storeId,Long keywordId){

        Store store = storeRepository.findById(storeId);
        Keyword keyword = keywordRepository.findById(keywordId);

        StoreKeyword storeKeyword=StoreKeyword.createStoreKeyword(keyword);

        store.getStoreKeywords().add(storeKeyword);
        storeKeyword.setStore(store);

        storeKeywordRepository.save(storeKeyword);
    }

    public StoreDTO setStoreDTO(Store store) {

        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setName(store.getName());
        storeDTO.setImagePath(store.getImagePath());
        storeDTO.setTableCount(store.getTableCount());
        storeDTO.setInfo(store.getInfo());
        storeDTO.setRestTableCount(store.getRestTableCount());
        storeDTO.setLocationName(store.getLocationName());
        storeDTO.setLocationX(store.getLocationX());
        storeDTO.setLocationY(store.getLocationY());

        Address address = store.getAddress();
        String ad="";
        ad= address.getTown();
        /*if(address.getState()==null) {
            ad = address.getCity() + " " + address.getTown()
                    + " " + address.getStreet() + " " + address.getDetailAddress();
        } else {
            ad = address.getState() + " " + address.getCity() + " " + address.getTown()
                    + " " + address.getStreet() + " " + address.getDetailAddress();
        }*/
        storeDTO.setAddress(ad);
        return storeDTO;
    }

    public List<StoreDTO> setStoreDTO(List<Store> storeList){

        List<StoreDTO> stores = new ArrayList<>();
        for(Store store:storeList){
            StoreDTO storeDTO = setStoreDTO(store);
            stores.add(storeDTO);
        }
        return stores;
    }


    public Address splitAddress(String ad) {
        String s="";
        List<String> list = new ArrayList<>();
        boolean check=false;
        int index=0;
        for(int i=0;i<ad.length();i++){
            if(ad.charAt(i)!=' '){
                check=true;
                index=i;
                break;
            }
        }
        int count=0;
        if(check){
            for(int i = index; i< ad.length(); i++){
                if(ad.charAt(i)==' ') {
                    if(count==0 && ad.charAt(i-1)=='시') {
                        count++;
                        list.add(null);
                    }
                    if(s=="") continue;
                    list.add(s);
                }
                else {
                    s+= ad.charAt(i);
                    continue;
                }
                s="";
            }
        }
        list.add(s);
        String str="";
        for(int i=4;i<list.size();i++){
            str+=list.get(i)+' ';
        }
        Address address = new Address(list.get(0),list.get(1),list.get(2),list.get(3),str);
        return address;
    }

    /*public List<StoreKeyword> findKeywordStores(String keyword){
        return storeRepository.findAllByString(keyword);
    }*/

    public List<Store> findKeywordStores(List<String> keywords) {
        List<Store> stores = findStores();
        System.out.println(stores.size());
        List<Store> storeList = new ArrayList<>();

        for(Store store:stores){
            int count=0;
            List<StoreKeyword> storeKeywords = store.getStoreKeywords();
            for(String keyword:keywords) {
                for (StoreKeyword storeKeyword : storeKeywords) {
                    if (storeKeyword.getKeyword().getName().equals(keyword)) {
                        count++;
                        break;
                    }
                }
            }
            if (count == keywords.size()) storeList.add(store);
        }
        return storeList;
    }
}
