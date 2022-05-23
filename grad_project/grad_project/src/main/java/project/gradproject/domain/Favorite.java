package project.gradproject.domain;


import lombok.Getter;
import lombok.Setter;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Favorite {

    @Id
    @GeneratedValue
    @Column(name="favorite_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="store_id")
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public static Favorite createFavorite(Store store, User user) {
        Favorite favorite = new Favorite();
        favorite.setStore(store);
        favorite.setUser(user);
        return favorite;
    }


}
