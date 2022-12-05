package catharsis.alert_server.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface SystemAlertRepository extends JpaRepository<SystemAlert, Integer> {
    List<SystemAlert> findAllByTimestampGreaterThanEqual(Timestamp timestamp);
}
