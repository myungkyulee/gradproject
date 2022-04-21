package project.gradproject.domain;

import lombok.Data;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class StoreJoinForm {

    @NotEmpty
    private String name;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String address;
    @NotNull(message = "비어 있을 수 없습니다")
    private Integer tableCount;
}
