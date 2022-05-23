package project.gradproject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.gradproject.domain.Favorite;
import project.gradproject.domain.waiting.Waiting;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FavoriteRepository {

    private final EntityManager em;

    public void save(Favorite favorite){
        em.persist(favorite);
    }

    public void remove(Favorite favorite) {
        em.remove(favorite);
    }

}
