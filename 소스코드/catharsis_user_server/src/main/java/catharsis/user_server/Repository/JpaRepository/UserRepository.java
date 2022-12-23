package catharsis.user_server.Repository.JpaRepository;

import catharsis.user_server.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    public Optional<User> findByNickname(@Param("nickname") final String Nickname);

}
