package catharsis.alert_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name="user_session")
public class UserSession {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer session_id;

    @Column(name="user_id", length=20, nullable = false)
    private String user_id;

    @Column(name="last_timestamp", nullable = false)
    private Timestamp last_timestamp;
}
