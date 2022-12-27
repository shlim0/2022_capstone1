package catharsis.space_server.entity_repository;

import catharsis.space_server.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceRepository extends JpaRepository<Space, Integer> {
    List<Space> findAllByUserId(String userId);

    List<Space> findAllByLatitudeGreaterThanEqualAndLatitudeLessThanEqualAndLongitudeGreaterThanEqualAndLongitudeLessThanEqual(Double begin_latitude, Double end_latitude, Double begin_longitude, Double end_longitude);
}
