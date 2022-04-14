package project.gradproject.domain.user;

import lombok.Getter;
import lombok.Setter;
import project.gradproject.domain.waiting.Waiting;

import javax.persistence.*;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name="user_id")
    private Long id;


    private String name;

    // 로그인 아이디 패스워드
    private String loginId;
    private String loginPassword;

    @OneToOne(mappedBy = "user")
    private Waiting wating;
}
