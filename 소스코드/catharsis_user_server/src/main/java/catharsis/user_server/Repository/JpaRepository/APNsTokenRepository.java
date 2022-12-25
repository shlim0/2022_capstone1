package catharsis.user_server.Repository.JpaRepository;

import catharsis.user_server.Entity.APNsToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface APNsTokenRepository extends JpaRepository<APNsToken, String> {
    public List<APNsToken> findAllByUserId (@Param("userId") String UserId);

}
