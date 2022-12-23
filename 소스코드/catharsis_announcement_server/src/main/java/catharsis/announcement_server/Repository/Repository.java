package catharsis.announcement_server.Repository;

import catharsis.announcement_server.Entity.SystemAlert;
import catharsis.announcement_server.Repository.JpaRepository.SystemAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Repository {

    @Autowired
    private SystemAlertRepository systemAlertRepository;

    public SystemAlert insert_system_message(SystemAlert system_alert) {
        return systemAlertRepository.save(system_alert);
    }

}
