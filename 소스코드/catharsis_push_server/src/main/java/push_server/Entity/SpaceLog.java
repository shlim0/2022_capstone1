package push_server.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "space_log")
@NoArgsConstructor
@AllArgsConstructor
public class SpaceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false)
    private Integer logId;

    @Column(name = "space_id", nullable = false)
    private Integer spaceId;

    @Column(name = "log_status_code", nullable = false)
    private Integer logStatusCode;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

}
