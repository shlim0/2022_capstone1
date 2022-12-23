package chat_room_manage_server.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_session")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id", nullable = false)
    private Integer sessionId;

    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @Column(name = "latest_timestamp", nullable = false)
    private Timestamp latestTimestamp;

}
