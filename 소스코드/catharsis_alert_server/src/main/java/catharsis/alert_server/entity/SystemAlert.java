package catharsis.alert_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name="system_alert")
public class SystemAlert {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer alert_id;

    @Column(name="timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name="message", length=255)
    private String message;
}
