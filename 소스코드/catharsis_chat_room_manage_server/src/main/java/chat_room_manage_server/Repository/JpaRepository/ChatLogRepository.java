package chat_room_manage_server.Repository.JpaRepository;

import chat_room_manage_server.Entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLog, Integer> {

    public List<ChatLog> findAllByChatRoomIdAndTimestampGreaterThanEqual(
            @Param("chatRoomId") final Integer ChatRoomId,
            @Param("timestamp") final Timestamp Timestamp
    );

}
