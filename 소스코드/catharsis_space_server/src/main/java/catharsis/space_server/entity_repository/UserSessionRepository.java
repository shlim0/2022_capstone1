package catharsis.space_server.entity_repository;

import catharsis.space_server.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
    Optional<UserSession> findById(Integer id);
}
