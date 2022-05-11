package project.gradproject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.gradproject.domain.waiting.Waiting;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class WaitingRepository {

    private final EntityManager em;

    public void save(Waiting waiting){
        em.persist(waiting);
    }

    public Waiting findById(Long waitingId){
        return em.find(Waiting.class, waitingId);
    }

    public List<Waiting> findStoreWaitingList(Long storeId) {

        String jpql = "select w From Waiting w join w.store s " +
                "where s.id = :storeId";
        List<Waiting> w = em.createQuery(jpql, Waiting.class).setParameter("storeId", storeId)
                .getResultList();
        return w;
    }
}
