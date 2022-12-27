package catharsis.space_server.entity_repository;

import catharsis.space_server.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface TagsRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByTag(String tag);
}
