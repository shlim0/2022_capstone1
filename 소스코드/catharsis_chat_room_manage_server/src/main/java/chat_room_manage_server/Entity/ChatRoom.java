package chat_room_manage_server.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id", nullable = false)
    private Integer chatRoomId;

    @Column(name = "user_id_1", nullable = false, length = 20)
    private String userOneId;

    @Column(name = "user_id_2", nullable = false, length = 20)
    private String userTwoId;

    @Column(name = "user_1_leave", nullable = false)
    private Boolean userOneLeft;

    @Column(name = "user_2_leave", nullable = false)
    private Boolean userTwoLeft;

}
