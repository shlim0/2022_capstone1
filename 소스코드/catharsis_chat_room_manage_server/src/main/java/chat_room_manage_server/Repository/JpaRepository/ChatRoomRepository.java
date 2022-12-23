package chat_room_manage_server.Repository.JpaRepository;

import chat_room_manage_server.Entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

}
