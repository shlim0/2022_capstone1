package catharsis.announcement_server.Repository.JpaRepository;

import catharsis.announcement_server.Entity.SystemAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemAlertRepository extends JpaRepository<SystemAlert, Integer> {

}
