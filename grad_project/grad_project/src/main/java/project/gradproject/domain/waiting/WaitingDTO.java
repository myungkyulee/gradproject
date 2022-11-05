package project.gradproject.domain.waiting;

import lombok.Data;


@Data
public class WaitingDTO {
    private String time;
    private int num;
    private Waiting waiting;
}
