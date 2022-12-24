package push_server.Repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import push_server.Entity.SpaceLog;

public interface SpaceLogRepository extends JpaRepository<SpaceLog, Integer> {

}
