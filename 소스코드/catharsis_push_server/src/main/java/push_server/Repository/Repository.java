package push_server.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import push_server.Entity.*;
import push_server.Repository.JpaRepository.*;

import java.util.List;

public class Repository {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private APNsTokenRepository apnsTokenRepository;

    @Autowired
    private ChatLogRepository chatLogRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private SpaceLogRepository spaceLogRepository;

    //로그 아이디를 받고 해당하는 로그 반환
    public SpaceLog get_space_log(final int space_log_id) {
        return spaceLogRepository.findById(space_log_id).orElseThrow();
    }

    //공간 아이디를 넘겨받고 해당 공간을 선호하는 유저들의 목록에서 아이디를 추출해 리스트로 반환
    public List<String> get_favorite_user_id_list_by_space_id(final int space_id) {
        List<String> user_id_list = null;
        final List<Favorite> q_res = favoriteRepository.findAllBySpaceId(space_id);

        for(Favorite fav : q_res) {
            user_id_list.add(fav.getUserId());
        }

        return user_id_list;
    }
    
    //유저 아이디를 받고 해당 유저와 연결된 토큰 목록 반환
    public List<APNsToken> get_user_APNS_token_list(final String user_id) {
        return apnsTokenRepository.findAllByUserId(user_id);
    }

    //공지 아이디를 받고 해당하는 공지를 반환
    public Announcement get_announcement(final int announcement_id) {
        return announcementRepository.findById(announcement_id).orElseThrow();
    }

    //토큰과 계정의 연결 여부와 상관없이 모든 디바이스 토큰을 찾아 리스트로 반환
    public List<APNsToken> get_all_APNS_token_list() {
        return apnsTokenRepository.findAll();
    }

    //채팅 아이디를 받아서 해당하는 채팅 기록을 반환
    public ChatLog get_chat_log(final int chat_log_id) {
        return chatLogRepository.findById(chat_log_id).orElseThrow();
    }
    
    //채팅방 아이디를 받아서 해당하는 채팅방 반환
    public ChatRoom get_chat_room(final int chat_room_id) {
        return chatRoomRepository.findById(chat_room_id).orElseThrow();
    }

}
