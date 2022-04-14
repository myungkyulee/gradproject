package project.gradproject.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.gradproject.domain.store.Store;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StoreRepository {

    private final EntityManager em;

    public void save(Store store){
        em.persist(store);
    }

    public Store findById(Long storeId){
        return em.find(Store.class, storeId);
    }

    public Optional<Store> findByLoginId(String loginId){
        /*List<Store> all =findAll();
        for(Store m : all){
            return Optional.of(m);
        }
        return Optional.empty();
*/
        return findAll().stream()
                .filter(m-> m.getLoginId().equals(loginId))
                .findFirst();
    }


    public List<Store> findByName(String name){
        return em.createQuery("select s from Store s where s.name = :name",Store.class)
                .setParameter("name",name)
                .getResultList();
    }

    public List<Store> findAll(){
        return em.createQuery("select s from Store s", Store.class)
                .getResultList();
    }





}
