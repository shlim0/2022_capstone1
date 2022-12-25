package chat_room_manage_server.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static chat_room_manage_server.Config.Config.MAX_INFORMATION_LENGTH;

@Data
@Entity
@Table(name = "user_session")
@NoArgsConstructor
@AllArgsConstructor
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
