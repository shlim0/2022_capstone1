package push_server.Repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import push_server.Entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

}
