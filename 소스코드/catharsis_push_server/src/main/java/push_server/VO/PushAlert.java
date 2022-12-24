package push_server.VO;

import lombok.Data;
import push_server.Entity.Announcement;
import push_server.Entity.ChatLog;
import push_server.Entity.SpaceLog;

import java.sql.Timestamp;

@Data
public class PushAlert {

    private String message;

    private Timestamp timestamp;

    public PushAlert(final Announcement announcement) {
        this.message = announcement.getMessage();
        this.timestamp = announcement.getTimestamp();
    }

    public PushAlert(final ChatLog chat_log) {
        this.timestamp = chat_log.getTimestamp();
        String msg = null;
        switch(chat_log.getChatStatusCode()) {
            case 0 :
                msg = chat_log.getUserId() + " : " + chat_log.getMessage() + '\n';
                break;
            case 1 :
                msg = "사진\n";
                break;
            case 2 :
            case 3 :
                msg = "상대방이 채팅방을 나갔습니다.\n";
                break;
            default :
                break;
        }
        this.message = msg;
    }

    public PushAlert(final SpaceLog space_log) {
        this.timestamp = space_log.getTimestamp();
        String log = null;
        switch(space_log.getLogStatusCode()) {
            case 1 :
                log = "내 관심 장소가 대여되었습니다.\n";
                break;
            case 2 :
                log = "내 관심 장소가 삭제되었습니다.\n";
                break;
            case 3 :
                log = "내 관심 장소의 이미지가 변경되었습니다.\n";
                break;
            case 4 :
                log = "내 관심 장소의 제목이 변경되었습니다.\n";
                break;
            case 5 :
                log = "내 관심 장소의 위치가 변경되었습니다.\n";
                break;
            case 6 :
                log = "내 관심 장소의 설명이 변경되었습니다.\n";
                break;
            case 7 :
                log = "내 관심 장소의 태그가 변경되었습니다.\n";
                break;
            default :
                break;
        }
        this.message = log;
    }

}
