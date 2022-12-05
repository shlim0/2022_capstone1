package catharsis.alert_server.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
    UserSession findById(int session_id);
}
