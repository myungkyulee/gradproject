package project.gradproject.domain;


import lombok.Data;
import project.gradproject.domain.waiting.Waiting;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Data
public class UserJoinForm {

    @NotEmpty
    private String name;

    // 로그인 아이디 패스워드
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
