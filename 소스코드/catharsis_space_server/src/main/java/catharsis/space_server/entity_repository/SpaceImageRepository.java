package catharsis.space_server.entity_repository;

import catharsis.space_server.entity.SpaceImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceImageRepository extends JpaRepository<SpaceImage, Integer> {
    void deleteAllBySpaceId(Integer spaceId);

    List<SpaceImage> findAllBySpaceId(Integer spaceId);
}
