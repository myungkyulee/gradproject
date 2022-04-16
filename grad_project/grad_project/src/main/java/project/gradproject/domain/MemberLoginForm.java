package project.gradproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberLoginForm {

    @NotEmpty
    private String type;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

}
