package chat_room_manage_server.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import static chat_room_manage_server.Config.Config.MAX_INFORMATION_LENGTH;

@Data
@Entity
@Table(name = "chat_log")
@NoArgsConstructor
@AllArgsConstructor
public class ChatLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", nullable = false)
    private Integer chatId;

    @Column(name = "chat_room_id", nullable = false)
    private Integer chatRoomId;

    @Column(name = "user_id", nullable = false, length = MAX_INFORMATION_LENGTH)
    private String userId;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    // 0 : 텍스트, 1 : 이미지, 2 : 유저1 퇴장, 3 : 유저2 퇴장
    @Column(name = "chat_status_code", nullable = false)
    private Integer chatStatusCode;

    public ChatLog (
            final Integer chatRoomId,
            final String userId,
            final String message,
            final Timestamp timestamp,
            final Integer chatStatusCode
    ) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.message = message;
        this. timestamp = timestamp;
        this.chatStatusCode = chatStatusCode;
    }

}
