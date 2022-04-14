package project.gradproject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findById(Long userId){
        return em.find(User.class, userId);
    }

    public List<User> findByName(String name){
        return em.createQuery("select u from User u where u.name = :name",User.class)
                .setParameter("name",name)
                .getResultList();
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

}
