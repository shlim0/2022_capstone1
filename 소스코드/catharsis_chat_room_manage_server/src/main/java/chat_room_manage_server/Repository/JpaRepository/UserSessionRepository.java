package chat_room_manage_server.Repository.JpaRepository;

import chat_room_manage_server.Entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {

}
