package catharsis.announcement_server.Entity;

import catharsis.announcement_server.Config.Config;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "system_alert")
public class SystemAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer alert_id;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "message", length = Config.MAX_MESSAGE_LENGTH)
    private String message;

    public SystemAlert(final String message, final Timestamp timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

}
