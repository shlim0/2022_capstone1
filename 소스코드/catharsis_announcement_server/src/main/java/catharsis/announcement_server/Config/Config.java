package catharsis.announcement_server.Config;
public class Config {

    public static final String PUSH_SERVER_IP = "119.192.59.11"; //푸시서버 IP 주소

    public static final String PUSH_SERVER_PORT = ":8080"; //푸시서버 PORT 값

    public static final String POST_PATH = "/push-system-message"; //POST 요청을 보낼 URL

    public static final int MAX_MESSAGE_LENGTH = 255; //공지 메시지 최대 길이

    public static final int TIME_OUT_MS = 5000; // 타임아웃 제한시간(1000MS = 1초)

}
