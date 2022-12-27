package catharsis.space_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="user_session")
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="session_id", nullable = false)
    private Integer id;

    @Column(name="user_id", length=20, nullable = false)
    private String userId;

    @Column(name="last_timestamp", nullable = false)
    private Timestamp lastTimestamp;
}
