package catharsis.space_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="favorite_id", nullable = false)
    private Integer id;
    @Column(name="user_id", length=20, nullable = false)
    private String userId;
    @Column(name="space_id", nullable = false)
    private Integer spaceId;

    public Favorite(String userId, Integer spaceId) {
        this.userId = userId;
        this.spaceId = spaceId;
    }
}
