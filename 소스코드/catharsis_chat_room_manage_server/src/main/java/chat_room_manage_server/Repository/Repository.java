package chat_room_manage_server.Repository;

import chat_room_manage_server.Repository.JpaRepository.ChatRoomRepository;
import chat_room_manage_server.Repository.JpaRepository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Repository {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

}

