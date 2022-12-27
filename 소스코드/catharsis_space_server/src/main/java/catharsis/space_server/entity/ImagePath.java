package catharsis.space_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="image_path")
public class ImagePath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_path_id", nullable = false)
    private Integer id;

    @Column(name="image_path", length=100, nullable = false)
    private String imagePath;

    public ImagePath(String image_path) {
        this.imagePath = image_path;
    }
}
