package catharsis.space_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="space_image")
public class SpaceImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="space_image_id", nullable = false)
    private Integer id;

    @Column(name="space_id", nullable = false)
    private Integer spaceId;

    @Column(name="image_path_id", nullable = false)
    private Integer imagePathId;

    public SpaceImage(Integer spaceId, Integer imagePathId) {
        this.spaceId = spaceId;
        this.imagePathId = imagePathId;
    }
}
