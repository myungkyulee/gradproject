package project.gradproject.domain.user;

import lombok.*;
import project.gradproject.domain.Favorite;
import project.gradproject.domain.Member;
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
public class User implements Member {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String name;

    // 로그인 아이디 패스워드
    private String email;
    private String password;

    // 권한
    private String role;

    // 주소, 위치
    private String locationName;
    private Double locationX;
    private Double locationY;

    @OneToMany(mappedBy = "user")
    private List<Favorite> Favorites = new ArrayList<>();  // 찜하기 리스트

    @OneToMany(mappedBy = "user")
    private List<Waiting> waitingList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<SearchWord> searchWords = new ArrayList<>();
}
