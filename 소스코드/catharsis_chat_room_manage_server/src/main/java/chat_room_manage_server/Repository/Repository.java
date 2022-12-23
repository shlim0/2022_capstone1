package chat_room_manage_server.Repository;

import chat_room_manage_server.Entity.ChatLog;
import chat_room_manage_server.Entity.ChatRoom;
import chat_room_manage_server.Repository.JpaRepository.ChatLogRepository;
import chat_room_manage_server.Repository.JpaRepository.ChatRoomRepository;
import chat_room_manage_server.Repository.JpaRepository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Repository {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private ChatLogRepository chatLogRepository;

    //해당 채팅방에 속하는 타임스탬프 이후의 메시지를 모두 반환
    public List<ChatLog> get_chat_log(final int chat_room_id, final Timestamp timestamp) {
        return chatLogRepository.findAllByChatRoomIdAndTimestampGreaterThanEqual(chat_room_id, timestamp);
    }

    //세션 아이디로 유저 아이디를 얻음
    public String get_user_id_by_session_id(final int session_id) {
        return userSessionRepository.findById(session_id).orElseThrow().getUserId();
    }

    //세션 아이디로 유저 아이디를 얻어서 해당 유저가 속해있는 채팅방을 모두 반환
    public List<ChatRoom> get_chat_room_list_by_session_id(final int session_id) {
        final String user_id = get_user_id_by_session_id(session_id);
        return chatRoomRepository.findAllByUserOneIdOrUserTwoId(user_id, user_id);
    }

    //유저1 아이디와 유저2 아이디를 받아 채팅방을 생성 후 개체를 반환
    public ChatRoom create_chat_room(
            final String user_id_1,
            final String user_id_2
    ) {
        return chatRoomRepository.save(
                new ChatRoom(
                        user_id_1,
                        user_id_2
                )
        );
    }

    //채팅방 아이디와 유저 아이디를 받아 해당 채팅방 내의 해당 유저의 퇴장 여부를 true로 변경
    public void set_leave_chat_room(final int chat_room_id, final String user_id) {
        ChatRoom room = chatRoomRepository.findById(chat_room_id).orElseThrow();
        if(room.getUserOneId().equals(user_id)) {
            room.setUserOneLeft(true);
        }
        else if(room.getUserTwoId().equals(user_id)) {
            room.setUserTwoLeft(true);
        }
    }

    //채팅방 아이디와 유저 아이디를 받아 해당 유저가 해당 채팅방에서 나간 기록을 ChatLogRepository에 남김
    public void add_leave_message(final int chat_room_id, final String user_id) {
        ChatRoom room = chatRoomRepository.findById(chat_room_id).orElseThrow();
        Optional<Integer> code = Optional.empty();
        String name = null;

        if(user_id.equals(room.getUserOneId())) {
            code = Optional.of(2);
            name = room.getUserOneId();
        }
        else if(user_id.equals(room.getUserTwoId())) {
            code = Optional.of(3);
            name = room.getUserTwoId();
        }

        if(code.isPresent()) {
            chatLogRepository.save(
                    new ChatLog (
                            chat_room_id,
                            name,
                            name + " 님이 채팅방을 나갔습니다.",
                            Timestamp.valueOf(LocalDateTime.now()),
                            code.get()
                    )
            );
        }
    }
}

