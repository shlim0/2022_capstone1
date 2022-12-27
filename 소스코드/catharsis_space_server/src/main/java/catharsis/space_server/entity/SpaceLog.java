package catharsis.space_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="space_log")
public class SpaceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="log_id", nullable = false)
    private Integer id;

    @Column(name="space_id", nullable = false)
    private Integer spaceId;

    @Column(name="log_status_code", nullable = false)
    private Integer logStatusCode;

    @Column(name="timestamp", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Timestamp date;

    public SpaceLog(Integer spaceId, Integer logStatusCode) {
        this.spaceId = spaceId;
        this.logStatusCode = logStatusCode;
        this.date = Timestamp.valueOf(LocalDateTime.now());
    }

}
