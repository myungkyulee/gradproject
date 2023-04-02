package project.gradproject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.gradproject.domain.waiting.Waiting;
import project.gradproject.domain.waiting.WaitingStatus;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    public List<Waiting> findStoreCurrentWaitingList(Long storeId) {
        String jpql = "select w From Waiting w join w.store s " +
                "where s.id = :storeId AND w.status = :status " +
                "order by w.createdAt ASC";

        return em.createQuery(jpql, Waiting.class)
                .setParameter("storeId", storeId)
                .setParameter("status", WaitingStatus.WAIT)
                .getResultList();
    }

    public List<Waiting> findStoreLastWaitingList(Long storeId) {
        String jpql = "select w From Waiting w join w.store s " +
                "where s.id = :storeId AND w.status != :status " +
                "order by w.createdAt DESC";

        return em.createQuery(jpql, Waiting.class)
                .setParameter("storeId", storeId)
                .setParameter("status", WaitingStatus.WAIT)
                .getResultList();
    }
}
