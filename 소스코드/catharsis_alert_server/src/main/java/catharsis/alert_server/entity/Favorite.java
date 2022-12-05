package catharsis.alert_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer favorite_id;

    @Column(name="user_id", length=20, nullable = false)
    private String user;

    @Column(name="space_id", nullable=false)
    private Integer space;
}
