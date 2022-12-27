package catharsis.space_server.entity_repository;

import catharsis.space_server.entity.SpaceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceLogRepository extends JpaRepository<SpaceLog, Integer> {
}
