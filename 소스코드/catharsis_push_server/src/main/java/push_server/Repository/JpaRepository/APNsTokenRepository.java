package push_server.Repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import push_server.Entity.APNsToken;

import java.util.List;

public interface APNsTokenRepository extends JpaRepository<APNsToken, String> {

      public  List<APNsToken> findAllByUserId(@Param("userId") final String UserId);

}
