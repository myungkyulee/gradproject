package project.gradproject.domain;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserJoinForm {
    @NotEmpty
    private String name;

    // 로그인 아이디 패스워드
    @NotEmpty
    @Email
    private String username;
    @NotEmpty
    private String password;
}
