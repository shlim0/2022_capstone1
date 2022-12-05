package catharsis.alert_server.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface SpaceLogRepository  extends JpaRepository<SpaceLog, Integer> {
    public List<SpaceLog> findAllBySpaceAndTimestampGreaterThanEqual(int space_id, Timestamp timestamp);
}
