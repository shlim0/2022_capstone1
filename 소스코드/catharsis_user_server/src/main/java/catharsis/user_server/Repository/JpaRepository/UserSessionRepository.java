package catharsis.user_server.Repository.JpaRepository;

import catharsis.user_server.Entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {

    List<UserSession> findAllByUserId(@Param("userId") final String UserId);

}
