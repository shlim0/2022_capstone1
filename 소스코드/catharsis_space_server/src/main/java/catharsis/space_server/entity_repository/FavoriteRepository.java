package catharsis.space_server.entity_repository;

import catharsis.space_server.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    void deleteByUserIdAndSpaceId(String userId, Integer spaceId);

    List<Favorite> findAllByUserId(String userId);
}
