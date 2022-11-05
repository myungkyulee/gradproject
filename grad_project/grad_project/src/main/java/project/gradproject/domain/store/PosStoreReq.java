package project.gradproject.domain.store;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PosStoreReq {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotNull(message = "비어 있을 수 없습니다")
    private Integer tableCount;
    @NotEmpty
    private String info;
    @NotBlank
    private String address;
    private Double x;
    private Double y;
}
