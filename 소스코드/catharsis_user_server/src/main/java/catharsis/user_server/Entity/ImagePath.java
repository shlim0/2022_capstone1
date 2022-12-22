package catharsis.user_server.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "image_path")
public class ImagePath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_path_id", nullable = false)
    private Integer imagePathId;

    @Column(name = "image_path", nullable = false, unique = true)
    private String imagePath;

    public ImagePath(final String image_path) {
        this.imagePath = image_path;
    }

}
