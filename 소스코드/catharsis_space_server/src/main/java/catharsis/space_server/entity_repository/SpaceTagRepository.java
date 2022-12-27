package catharsis.space_server.entity_repository;

import catharsis.space_server.entity.SpaceTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpaceTagRepository extends JpaRepository<SpaceTag, Integer> {
    void deleteAllBySpaceId(Integer spaceId);

    Optional<SpaceTag> findBySpaceIdAndTagId(Integer spaceId, Integer tagId);

    List<SpaceTag> findAllBySpaceId(Integer spaceId);
}
