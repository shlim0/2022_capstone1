package catharsis.user_server.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "image_path")
@NoArgsConstructor
@AllArgsConstructor
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
