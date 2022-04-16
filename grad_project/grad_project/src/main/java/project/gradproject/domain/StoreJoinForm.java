package project.gradproject.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class StoreJoinForm {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String address;
    @NotBlank
    private String tableCount;
}
