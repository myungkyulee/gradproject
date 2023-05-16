package project.gradproject.domain.store;


import lombok.Getter;
import lombok.Setter;
import project.gradproject.domain.MemberType;
import project.gradproject.domain.waiting.Waiting;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Store {

    @Id
    @GeneratedValue
    @Column(name="store_id")
    private Long id;

    private String email;
    private String password;

    private String name;
    private String phoneNumber;

    @Enumerated
    private MemberType type;

    private Integer tableCount;  // 가게에 있는 총 테이블 개수

    private Integer restTableCount; // 가게에 비어있는 테이블 개수

    private String info; // 가게 소개

    private String imagePath; // 이미지 경로

    // 주소, 위치
    private String locationName;
    private Double locationX; // 경도
    private Double locationY; // 위도

    //권한
    private String role;


    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;  // 매장 오픈 여부

    @OneToMany(mappedBy = "store")
    private List<StoreKeyword> storeKeywords = new ArrayList<>();   // 검색을 위한 키워드 리스트

    @OneToMany(mappedBy = "store")
    private List<Waiting> waitingList = new ArrayList<>();  // 대기 리스트

    @Override
    public String toString() {
        return email +" "+ password +" "+name;
    }
}
