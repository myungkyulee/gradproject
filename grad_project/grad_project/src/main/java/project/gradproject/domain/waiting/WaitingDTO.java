package project.gradproject.domain.waiting;

import lombok.Data;
import project.gradproject.domain.store.Store;
import project.gradproject.domain.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@Data
public class WaitingDTO {

    String time;
    Waiting waiting;
}
