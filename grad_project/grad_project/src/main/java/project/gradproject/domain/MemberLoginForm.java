package project.gradproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberLoginForm {

    @NotEmpty
    private String type;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;

}
