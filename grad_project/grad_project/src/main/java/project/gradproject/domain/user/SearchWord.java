package project.gradproject.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;


@Entity
@Table(name = "search_word")
@Getter
@Setter
public class SearchWord {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name = "user_user_id")
    private User user;

    private String word;


    public void setUser(User user){
        this.user = user;
        user.getSearchWords().add(this);
    }



}