package push_server.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "APNs_token")
public class APNsToken {

    @Id
    @Column(name = "APNs_token", nullable = false, length = 100)
    private String aPNsToken;

    @Column(name = "user_id", length = 20)
    private String userId;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

}
