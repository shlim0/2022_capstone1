package push_server.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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

    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    // 0 : 텍스트, 1 : 이미지, 2 : 유저1 퇴장, 3 : 유저2 퇴장
    @Column(name = "chat_status_code", nullable = false)
    private Integer chatStatusCode;

}
