package project.gradproject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.gradproject.domain.Keyword;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.waiting.Waiting;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class KeywordRepository {

    private final EntityManager em;

    public void save(Keyword keyword){
        em.persist(keyword);
    }

    public Keyword findById(Long keywordId){
        return em.find(Keyword.class, keywordId);
    }

    public List<Keyword> findByName(String name){

        return em.createQuery("select k from Keyword k where k.name = :name", Keyword.class)
                .setParameter("name",name)
                .getResultList();
    }

}
