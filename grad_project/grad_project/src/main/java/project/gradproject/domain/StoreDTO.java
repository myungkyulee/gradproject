package project.gradproject.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class StoreDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotNull(message = "비어 있을 수 없습니다")
    private Integer tableCount;
    @NotNull(message = "비어 있을 수 없습니다")
    private Integer restTableCount;

    @NotEmpty
    private String info;
    @NotEmpty
    private String imagePath;
}
