package project.gradproject.repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.store.StoreKeyword;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StoreRepository {

    private final EntityManager em;

    public void save(Store store) {
        em.persist(store);
    }

    public Optional<Store> findById(Long storeId) {
        return Optional.ofNullable(em.find(Store.class, storeId));
    }

    public Optional<Store> findByEmail(String email) {
        return findAll().stream()
                .filter(m -> m.getEmail().equals(email))
                .findFirst();
    }


    public List<Store> findByName(String name) {
        return em.createQuery("select s from Store s where s.name = :name", Store.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Store> findStoresByDistance(Double longitude, Double latitude, int page) {
        log.info("[StoreRepository] findStoresByDistance");

        int size = 2;
        int offset = page * size;

        String jpql = "SELECT s FROM Store s " +
                "ORDER BY SQRT(POWER(:lat - s.locationY, 2) + POWER(:lng - s.locationX, 2)) ASC";

        return em.createQuery(jpql, Store.class)
                .setParameter("lat", latitude)
                .setParameter("lng", longitude)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    public List<Store> findStores(int page) {
        log.info("[StoreRepository] findStores");

        int size = 2;
        int offset = page * size;

        String jpql = "SELECT s FROM Store s";

        return em.createQuery(jpql, Store.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    public List<Store> findAll() {
        return em.createQuery("select s from Store s", Store.class)
                .getResultList();
    }

    public List<StoreKeyword> findAllByString(String keyword) {

        String jpql = "select s" +
                "from StoreKeyword sk, Keyword k, Store s" +
                "where sk.store_id = s.id and sk.keyword_id = k.id and k.name like :keyword";
        String jpql1 = "select sk from StoreKeyword sk";

        return em.createQuery(jpql1, StoreKeyword.class)
                .getResultList();
    }

    public boolean existsByEmail(String email) {
        String jpql = "select count(u) > 0 from Store s where s.email = :email";

        return em.createQuery(jpql, Boolean.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
