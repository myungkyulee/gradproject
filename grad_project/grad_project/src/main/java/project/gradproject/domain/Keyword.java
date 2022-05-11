package project.gradproject.domain;


import lombok.Getter;
import lombok.Setter;
import project.gradproject.domain.store.StoreKeyword;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
