package catharsis.user_server.Repository.JpaRepository;

import catharsis.user_server.Entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Integer> {
}
