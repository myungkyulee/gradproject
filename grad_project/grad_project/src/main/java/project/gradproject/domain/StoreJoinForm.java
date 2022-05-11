package project.gradproject.domain;

import lombok.Data;
import project.gradproject.domain.store.Address;


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
    @NotEmpty
    private String info;
    @NotEmpty
    private String imagePath;
}
