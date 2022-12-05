package catharsis.alert_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name="space_log")
public class SpaceLog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer log_id;

    @Column(name="space_id", nullable = false)
    private Integer space;

    @Column(name="log_status_code", nullable = false)
    private Integer log_status_code;

    @Column(name="timestamp", nullable = false)
    private Timestamp timestamp;
}
