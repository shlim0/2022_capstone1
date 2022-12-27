package catharsis.space_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tag_id", nullable = false)
    private Integer id;

    @Column(name="tag", length=100, nullable = false)
    private String tag;

    public Tag(String tag) {
        this.tag = tag;
    }
}
