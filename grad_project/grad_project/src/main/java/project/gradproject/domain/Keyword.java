package project.gradproject.domain;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class Keyword {

    @Id
    @GeneratedValue
    @Column(name = "keyword_id")
    private Long id;

    private String name;

}
