package project.gradproject.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    // 주소, 위치
    private String locationName;
    private Double locationX;
    private Double locationY;
    private Double dist;
}
