package catharsis.user_server.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static catharsis.user_server.Config.Config.*;

@Data
@Entity
@Table(name = "user_log")
@NoArgsConstructor
@AllArgsConstructor
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false)
    private Integer logId;

    @Column(name = "user_id", nullable = false, length = MAX_INFORMATION_LENGTH)
    private String userId;

    @Column(name = "log_status_code", nullable = false)
    private Integer logStatusCode;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "ip", nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String ip;

    public UserLog (final String user_id,
                    final Integer log_status_code,
                    final Timestamp timestamp,
                    final String ip) {
        this.userId = user_id;
        this.logStatusCode = log_status_code;
        this.timestamp = timestamp;
        this.ip = ip;
    }

}
