package project.gradproject.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.gradproject.domain.Keyword;
import project.gradproject.domain.store.StoreKeyword;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class StoreKeywordRepository {


    private final EntityManager em;

    public void save(StoreKeyword storeKeyword){
        em.persist(storeKeyword);
    }
}
