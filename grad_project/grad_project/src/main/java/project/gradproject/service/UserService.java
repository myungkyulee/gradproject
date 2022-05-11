package project.gradproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.waiting.WaitingStatus;
import project.gradproject.repository.StoreRepository;
import project.gradproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly =true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

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