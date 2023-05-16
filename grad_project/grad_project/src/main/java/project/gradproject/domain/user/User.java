package project.gradproject.domain.user;

import lombok.*;
import project.gradproject.domain.Favorite;
import project.gradproject.domain.MemberType;
import project.gradproject.domain.waiting.Waiting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // 권한
    @Enumerated
    private MemberType type;

    // 주소, 위치
    private String locationName;
    private Double locationX;
    private Double locationY;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Favorite> Favorites = new ArrayList<>();  // 찜하기 리스트

    @OneToMany(mappedBy = "user")
    private List<Waiting> waitingList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<SearchWord> searchWords = new ArrayList<>();
}
