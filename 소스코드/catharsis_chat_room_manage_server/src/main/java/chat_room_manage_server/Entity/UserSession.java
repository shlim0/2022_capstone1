package chat_room_manage_server.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

import static chat_room_manage_server.Config.Config.MAX_INFORMATION_LENGTH;

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

}
