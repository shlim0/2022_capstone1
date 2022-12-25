package push_server.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import push_server.Entity.*;
import push_server.Pusher.Pusher;
import push_server.Repository.Repository;
import push_server.VO.PushAlert;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

public class Controller {

    private final Pusher pusher;

    private final Repository repository;

    public Controller(final Pusher pusher, final Repository repository) {
        this.pusher = pusher;
        this.repository = repository;
    }

    //장소 정보가 갱신되는 경우
    @Transactional
    @PostMapping("/push-space-changed")
    public void push_space_change(final HttpServletRequest httpServletRequest) throws Exception {
        final Map<String, Object> obj = requestParser(httpServletRequest);
        //장소 로그 아이디를 전달받음
        final int space_log_id = (Integer) obj.get("space_log_id");
        //장소 로그 아이디로 장소 로그 검색
        final SpaceLog space_log = repository.get_space_log(space_log_id);
        //알림 생성
        final PushAlert push_alert = new PushAlert(space_log);
        //장소 아이디로 해당 장소를 관심 목록에 추가한 모든 유저의 리스트를 검색
        final List<String> user_list = repository.get_favorite_user_id_list_by_space_id(space_log.getSpaceId());
        
        //토큰 목록 초기화
        List<APNsToken> token_list = null;
        //유저 아이디로 해당 유저의 토큰을 모두 검색해서 리스트에 추가
        for(String user : user_list) {
            token_list.addAll(repository.get_user_APNS_token_list(user));
        }

        //리스트 내의 모든 디바이스에 푸시 알림 전송
        for(APNsToken token : token_list) {
            pusher.push_APNS_message(token, push_alert);
        }
    }

    //공지 알림을 받은 경우
    @Transactional
    @PostMapping("/push-system-message")
    public void push_system_message(final HttpServletRequest httpServletRequest) throws Exception {
        final Map<String, Object> obj = requestParser(httpServletRequest);
        //공지 아이디를 전달받음
        final int id = (Integer) obj.get("id");
        //공지 아이디로 공지 검색
        final Announcement announcement = repository.get_announcement(id);
        //알림 생성
        final PushAlert alert = new PushAlert(announcement);
        //모든 디바이스의 토큰 획득
        final List<APNsToken> token_list = repository.get_all_APNS_token_list();

        //디바이스마다 푸시알림 전송
        for(APNsToken token : token_list) {
            pusher.push_APNS_message(token, alert);
        }
    }

    //채팅이 온 경우
    @Transactional
    @PostMapping("/push-chat")
    public void push_chat(final HttpServletRequest httpServletRequest) throws Exception {
        //final String table_name = httpServletRequest.getParameter("table_name");
        final Map<String, Object> obj = requestParser(httpServletRequest);
        //채팅 아이디 전달받음
        final int chat_id = (Integer) obj.get("chat_id");
        //채팅 아이디로 채팅 기록 검색
        final ChatLog chat_log = repository.get_chat_log(chat_id);
        //알림 생성
        final PushAlert push_alert = new PushAlert(chat_log);
        //채팅 기록에서 채팅방 아이디 확보
        final ChatRoom chat_room = repository.get_chat_room(chat_log.getChatRoomId());

        //발화자와 수신자 구분 후 수신자의 토큰을 모두 찾아서 알림 전송
        if(chat_room.getUserOneId().equals(chat_log.getUserId())) { //발화자가 유저1라면 유저2에게 푸시 알림 전송
            final List<APNsToken> token_list = repository.get_user_APNS_token_list(chat_room.getUserTwoId());
            for(APNsToken token : token_list) {
                pusher.push_APNS_message(token, push_alert);
            }
        }
        else if(chat_room.getUserTwoId().equals(chat_log.getUserId())) { //발화자가 유저2라면 유저1에게 푸시 알림 전송
            final List<APNsToken> token_list = repository.get_user_APNS_token_list(chat_room.getUserOneId());
            for(APNsToken token : token_list) {
                pusher.push_APNS_message(token, push_alert);
            }
        }
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
