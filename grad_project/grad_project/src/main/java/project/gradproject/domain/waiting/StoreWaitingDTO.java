package project.gradproject.domain.waiting;

import lombok.Data;

@Data
public class StoreWaitingDTO {
    private Long id;
    private int num;
    private String username;
    private Integer peopleNum;
    private String time;
    private WaitingStatus status;



}
