package catharsis.announcement_server.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "system_alert")
public class SystemAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id", nullable = false)
    private Integer alert_id;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "message")
    private String message;

    public SystemAlert(final String message, final Timestamp timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

}
