package push_server.Repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import push_server.Entity.Favorite;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

    public List<Favorite> findAllBySpaceId(@Param("spaceId") final int SpaceId);

}
