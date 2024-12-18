package chat_room_manage_server.Controller;

import chat_room_manage_server.Entity.ChatLog;
import chat_room_manage_server.Entity.ChatRoom;
import chat_room_manage_server.Repository.Repository;
import chat_room_manage_server.VO.ChatRoomId;
import chat_room_manage_server.VO.ChatRoomList;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    private final Repository repository;

    @Autowired
    public Controller (final Repository repository) {
        this.repository = repository;
    }

    //채팅 내용 요청
    @Transactional
    @GetMapping("/chat")
    public List<ChatLog> get_chat_log(final HttpServletRequest httpServletRequest) throws Exception {
        final Map<String, Object> obj = requestParser(httpServletRequest);
        final int chat_room_id = (Integer) obj.get("chat_room_id");
        final Timestamp timestamp = (Timestamp) obj.get("timestamp");

        return repository.get_chat_log(chat_room_id, timestamp);
    }

    //채팅방 생성
    @Transactional
    @PostMapping("/chat")
    public ResponseEntity<ChatRoomId> create_chat_room(final HttpServletRequest httpServletRequest) throws Exception {
        final Map<String, Object> obj = requestParser(httpServletRequest);
        final int session_id_1 = (Integer) obj.get("user_id_1");
        final int session_id_2 = (Integer) obj.get("user_id_2");
        final String user_id_1 = repository.get_user_id_by_session_id(session_id_1);
        final String user_id_2 = repository.get_user_id_by_session_id(session_id_2);

        return new ResponseEntity<>(
                new ChatRoomId(repository.create_chat_room(user_id_1, user_id_2).getChatRoomId()),
                HttpStatus.OK
        );
    }

    //채팅방 목록 요청
    @Transactional
    @GetMapping("/chat-list")
    public ChatRoomList get_chat_room_list_by_session_id(final HttpServletRequest httpServletRequest) throws Exception {
        final Map<String, Object> obj = requestParser(httpServletRequest);
        final int session_id = (Integer) obj.get("session_id");
        List<ChatRoom> room_list = repository.get_chat_room_list_by_session_id(session_id);
        List<Integer> room_id_list = null;

        for(ChatRoom room : room_list) {
            room_id_list.add(room.getChatRoomId());
        }

        final Integer lsz = room_id_list.size();

        return new ChatRoomList(
                        lsz,
                        room_id_list.toArray(new Integer[lsz])
        );
    }

    //채팅방 퇴장
    @Transactional
    @DeleteMapping("/chat")
    public void leave_chat_room(final HttpServletRequest httpServletRequest) {
        final int chat_room_id = httpServletRequest.getIntHeader("chat_room_id");
        final int session_id = httpServletRequest.getIntHeader("session_id");
        final String user_id = repository.get_user_id_by_session_id(session_id);

        repository.set_leave_chat_room(chat_room_id, user_id);
        repository.add_leave_message(chat_room_id, user_id);
    }

    private Map<String, Object> requestParser (final HttpServletRequest request) throws Exception {
        StringBuffer jb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }

        JSONParser parser = new JSONParser(jb.toString());
        return (Map<String, Object>) parser.parse();
    }
}

