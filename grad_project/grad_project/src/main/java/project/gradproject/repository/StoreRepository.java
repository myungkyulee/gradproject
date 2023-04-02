package project.gradproject.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreKeyword;

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

    public Optional<Store> findByEmail(String email){
        return findAll().stream()
                .filter(m-> m.getEmail().equals(email))
                .findFirst();
    }


    public List<Store> findByName(String name){
        return em.createQuery("select s from Store s where s.name = :name",Store.class)
                .setParameter("name",name)
                .getResultList();
    }

 /*   public List<Store> findByStatus(StoreStatus status){
        return em.createQuery("select s from Store s where s.store_status = :status",Store.class)
                .setParameter("status",status)
                .getResultList();
    }
*/
    public List<Store> findAll(){
        return em.createQuery("select s from Store s", Store.class)
                .getResultList();
    }

    public List<StoreKeyword> findAllByString (String keyword){

        String jpql = "select s" +
                "from StoreKeyword sk, Keyword k, Store s" +
                "where sk.store_id = s.id and sk.keyword_id = k.id and k.name like :keyword";
        String jpql1="select sk from StoreKeyword sk";

        return em.createQuery(jpql1, StoreKeyword.class)
                .getResultList();

    }




}
