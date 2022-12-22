package catharsis.user_server.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

import static catharsis.user_server.Config.Config.*;

@Data
@Entity
@Table(name = "APNs_token")
public class APNsToken {

    @Id
    @Column(name = "APNs_token", nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String aPNsToken;

    @Column(name = "user_id", length = MAX_INFORMATION_LENGTH)
    private String userId;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    public APNsToken (final String APNs_token, final String user_id, final Timestamp timestamp) {
        this.aPNsToken = APNs_token;
        this.userId = user_id;
        this.timestamp = timestamp;
    }

}
