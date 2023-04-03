package project.gradproject.domain;

import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class StoreJoinForm {

    @NotEmpty
    private String name;
    @NotEmpty
    @Email
    private String username;
    @NotEmpty
    private String password;

    @NotNull(message = "비어 있을 수 없습니다")
    private Integer tableCount;
    @NotEmpty
    private String info;
    @NotEmpty
    private String imagePath;
    @NotEmpty(message = "비어 있을 수 없습니다")
    private String locationName;

    private Double locationX;

    private Double locationY;
}
