package push_server.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id", nullable = false)
    private Integer favoriteId;

    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @Column(name = "space_id", nullable = false)
    private Integer spaceId;

}
