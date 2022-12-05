package catharsis.alert_server.VO;

import catharsis.alert_server.entity.SpaceLog;
import catharsis.alert_server.entity.SystemAlert;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class Alert {
    public Alert(final Timestamp timestamp, final String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Alert(final SpaceLog spacelog) {
        this.timestamp = spacelog.getTimestamp();
        switch(spacelog.getLog_status_code()) {
            case 0:
                this.message = "" + spacelog.getSpace() + "번 장소가 등록되었습니다";
                break;
            case 1:
                this.message = "" + spacelog.getSpace() + "번 장소가 대여 완료되었습니다";
                break;
            case 2:
                this.message = "" + spacelog.getSpace() + "번 장소가 삭제되었습니다";
                break;
            case 3:
                this.message = "" + spacelog.getSpace() + "번 장소의 이미지가 변경되었습니다";
                break;
            case 4:
                this.message = "" + spacelog.getSpace() + "번 장소의 글 제목이 변경되었습니다";
                break;
            case 5:
                this.message = "" + spacelog.getSpace() + "번 장소의 위치가 변경되었습니다";
                break;
            case 6:
                this.message = "" + spacelog.getSpace() + "번 장소의 글 내용이 변경되었습니다";
                break;
            case 7:
                this.message = "" + spacelog.getSpace() + "번 장소의 태그가 변경되었습니다";
                break;
        }
    }

    public Alert(final SystemAlert system_alert) {
        this.timestamp = system_alert.getTimestamp();
        this.message = system_alert.getMessage();
    }

    private Timestamp timestamp;
    private String message;
}
