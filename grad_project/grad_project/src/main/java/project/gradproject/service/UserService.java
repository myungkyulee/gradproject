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

import java.util.List;
@Service
@Transactional(readOnly =true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


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
}