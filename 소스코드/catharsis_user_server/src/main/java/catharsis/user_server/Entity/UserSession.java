package catharsis.user_server.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

import static catharsis.user_server.Config.Config.*;

@Data
@Entity
@Table(name = "user_session")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", nullable = false)
    private Integer sessionId;

    @Column(name = "user_id", nullable = false, length = MAX_INFORMATION_LENGTH)
    private String userId;

    @Column(name = "latest_timestamp", nullable = false)
    private Timestamp latestTimestamp;

    public UserSession (final String user_id, final Timestamp timestamp) {
        this.userId = user_id;
        this.latestTimestamp = timestamp;
    }

}
