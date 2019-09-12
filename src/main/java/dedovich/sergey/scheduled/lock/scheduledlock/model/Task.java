package dedovich.sergey.scheduled.lock.scheduledlock.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String instanceId;
    private LocalDateTime creationDate = LocalDateTime.now();
}
