package project.gradproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.Favorite;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.waiting.WaitingStatus;
import project.gradproject.repository.FavoriteRepository;
import project.gradproject.repository.StoreRepository;
import project.gradproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly =true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final FavoriteRepository favoriteRepository;

    @Transactional
    public Long join(User user){
        // 중복회원 검증은 나중에
        /*List<Store> stores = storeRepository.findByName(store.getName());
        if(!stores.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원");
        }*/

        userRepository.save(user);
        return user.getId();
    }
    public List<User> findUsers(){
        return userRepository.findAll();
    }
    public User login(String loginId, String password){
        return userRepository.findbyloginId(loginId)
                .filter(user -> user.getLoginPassword().equals(password))
                .orElse(null);
    }

    public User findOne(Long userId){
        return userRepository.findById(userId);
    }


    public List<String> splitKeyword(String searchStr){
        String s="";
        List<String> list = new ArrayList<>();
        boolean check=false;
        int index=0;
        for(int i=0;i<searchStr.length();i++){
            if(searchStr.charAt(i)!=' '){
                check=true;
                index=i;
                break;
            }
        }

        if(check){
            for(int i = index; i< searchStr.length(); i++){
                if(searchStr.charAt(i)==' ') {
                    if(s=="") continue;
                    list.add(s);
                }
                else {
                    s+= searchStr.charAt(i);
                    continue;
                }
                s="";
            }
        }
        list.add(s);
        return list;
    }

    @Transactional
    public void favorite(Long storeId, Long userId){
        Store store = storeRepository.findById(storeId);
        User user = userRepository.findById(userId);
        Favorite favorite = Favorite.createFavorite(store, user);
        user.getFavorites().add(favorite);


        favoriteRepository.save(favorite);

    }
    @Transactional
    public void setLocation(User user, String s, Double x, Double y){
        user.setLocationName(s);
        user.setLocationX(x);
        user.setLocationY(y);
    }
    @Transactional
    public void setLocation(User user, String s){
        user.setLocationName(s);
    }


    @Transactional
    public void removeFavorite(User user, Favorite favorite){
        favoriteRepository.remove(favorite);
        user.getFavorites().remove(favorite);
    }



    // 모든 Store User와의 절대 거리가 짧은 순으로 정렬해서 리턴
    public List<Store> sortByDistance(User user){

        Double x = user.getLocationX();
        Double y = user.getLocationY();
        List<Store> stores = storeRepository.findAll();

        class StoreDist implements Comparable<StoreDist>{
            Double dist;
            Store store;

            public Store getStore() {
                return this.store;
            }
            public void setStore(Store store){
                this.store=store;
            }
            public void setDist(Double dist){
                this.dist=dist;
            }

            @Override
            public int compareTo(StoreDist o) {
                if(this.dist<o.dist){
                    return 1;
                }
                return -1;
            }
        }
        ArrayList<StoreDist> storeDists = new ArrayList<StoreDist>();

        for(int i=0;i<stores.size();i++){
            Store store = stores.get(i);
            Double storeX = store.getLocationX();
            Double storeY = store.getLocationY();

            Double xDist = Math.abs(storeX - x);
            Double yDist = Math.abs(storeY - y);

            Double dist=getDistance(x,y,storeX,storeY);
            StoreDist storeDist = new StoreDist();
            storeDist.setDist(dist);
            storeDist.setStore(store);
            storeDists.add(storeDist);
        }
        Collections.sort(storeDists, Collections.reverseOrder());

        List<Store> sortedStore = new ArrayList<Store>();
        for(int i=0;i<storeDists.size();i++){
            sortedStore.add(storeDists.get(i).getStore());
        }
        return sortedStore;
    }
    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d =6371* c * 1000;    // Distance in m
        return d;
    }

    /*public List<Store> checkKeyword(String searchStr){
        List<String> keywords = splitKeyword(searchStr);

        List<Store> stores = storeRepository.findAll();
        for(int i=0;i<keywords.size();i++){
            for(Store store : stores){
                Map<String, Boolean> key = store.getKeywords();
                if(key.get(keywords.get(i))){

                }
            }
        }


    }*/





}