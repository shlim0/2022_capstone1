package catharsis.space_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="space_tag")
public class SpaceTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="space_tag_id", nullable = false)
    private Integer id;

    @Column(name="space_id", nullable = false)
    private Integer spaceId;

    @Column(name="tag_id", nullable = false)
    private Integer tagId;

    public SpaceTag(Integer spaceId, Integer tagId) {
        this.spaceId = spaceId;
        this.tagId = tagId;
    }
}
