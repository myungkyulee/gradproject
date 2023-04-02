package project.gradproject.domain;


import lombok.Data;
import project.gradproject.domain.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserJoinForm {
    @NotEmpty
    private String name;

    // 로그인 아이디 패스워드
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
}
