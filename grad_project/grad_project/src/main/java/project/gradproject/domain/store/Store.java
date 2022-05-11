package project.gradproject.domain.store;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import project.gradproject.domain.waiting.Waiting;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Getter @Setter
public class Store  {

    @Id
    @GeneratedValue
    @Column(name="store_id")
    private Long id;

    private String loginId;
    private String loginPassword;

    private String name;

    @Embedded
    private Address address;

    private Integer tableCount;  // 가게에 있는 총 테이블 개수

    private Integer restTableCount; // 가게에 비어있는 테이블 개수

    private String info; // 가게 소개

    private String imagePath; // 이미지 경로


    @OneToMany(mappedBy = "store")
    private List<StoreKeyword> storeKeywords = new ArrayList<>();   // 검색을 위한 키워드 리스트


    @OneToMany(mappedBy = "store")
    private List<Waiting> waitingList = new ArrayList<>();  // 대기 리스트

    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;  // 매장 오픈 여부


    @Override
    public String toString() {
        return loginId+" "+loginPassword+" "+name;
    }
}
