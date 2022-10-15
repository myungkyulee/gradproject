package project.gradproject.domain.waiting;

import lombok.Getter;
import lombok.Setter;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Waiting {

    @Id
    @GeneratedValue
    @Column(name = "waiting_id")
    private Long id;

    @OneToOne(fetch= LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name="store_id")
    private Store store;

    private int peopleNum;

    private Timestamp createdAt;
    private Timestamp enteredAt;


    private WaitingStatus status;

    public void setUser(User user){
        this.user=user;
        user.getWaitingList().add(this);
    }


    public static Waiting createWaiting(User user, Store store, int peopleNum) {
        Waiting waiting = new Waiting();
        waiting.setUser(user);
        waiting.setStore(store);
        waiting.setPeopleNum(peopleNum);
        waiting.setStatus(WaitingStatus.WAIT);
        waiting.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return waiting;
    }
    public void setStore(Store store){
        this.store=store;
        store.getWaitingList().add(this);
    }
    public void enter() {
        this.setEnteredAt(new Timestamp(System.currentTimeMillis()));
        this.setStatus(WaitingStatus.ENTER);
    }

    @Override
    public String toString() {
        return "id = " + id +" userName = "+user.getName() +" storeName = " + store.getName();
    }


}