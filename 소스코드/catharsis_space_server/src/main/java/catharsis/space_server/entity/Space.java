package catharsis.space_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="space")
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="space_id", nullable = false)
    private Integer id;

    @Column(name="user_id", length=20, nullable = false)
    private String userId;

    @Column(name="title", length=100, nullable = false)
    private String title;

    @Column(name="latitude", nullable = false)
    private Double latitude;

    @Column(name="longitude", nullable = false)
    private Double longitude;

    @Column(name="description")
    private String description;

    @Column(name="status", nullable = false)
    private Integer status;
}
