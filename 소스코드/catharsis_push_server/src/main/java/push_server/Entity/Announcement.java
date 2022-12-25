package push_server.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "system_alert")
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id", nullable = false)
    private Integer alertId;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "message")
    private String message;

}
