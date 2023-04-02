package project.gradproject.domain.store;


import lombok.Getter;
import lombok.Setter;
import project.gradproject.domain.Keyword;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class StoreKeyword {
    @Id
    @GeneratedValue
    @Column(name = "store_keyword_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="store_id")
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="keyword_id")
    private Keyword keyword;

    public static StoreKeyword createStoreKeyword(Keyword keyword){
        StoreKeyword storeKeyword = new StoreKeyword();
        storeKeyword.setKeyword(keyword);

        return storeKeyword;
    }
}
