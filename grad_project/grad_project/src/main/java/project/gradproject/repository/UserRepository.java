package project.gradproject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.user.SearchWord;
import project.gradproject.domain.user.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    @Transactional
    public void save(User user){
        em.persist(user);
    }

    public User findById(Long userId){
        return em.find(User.class, userId);
    }

    public List<User> findByName(String name){
        TypedQuery<User> name1 = em.createQuery("select u from User u where u.name = :name", User.class);
        return em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public boolean existsByEmail(String email){
        String jpql = "select count(u) > 0 from User u where u.email = :email";
        TypedQuery<Boolean> query = em.createQuery(jpql, Boolean.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    public Optional<User> findByEmail(String email){
        String jpql = "select u from User u where u.email = :email";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("email", email);
        return Optional.ofNullable(query.getSingleResult());
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public void saveSearchWord(SearchWord searchWord) {
        em.persist(searchWord);
    }

   /* public Favorite findUserFavorite(){
        return em.createQuery("select f from Favorite f, User u", Favorite.class)
                .getResultList();
    }*/
}
