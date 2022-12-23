package chat_room_manage_server.Repository.JpaRepository;

import chat_room_manage_server.Entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    public List<ChatRoom> findAllByUserOneIdOrUserTwoId (
            @Param("userOneId") final String UserOneId,
            @Param("userTwoId") final String UserTwoId
    );

}
