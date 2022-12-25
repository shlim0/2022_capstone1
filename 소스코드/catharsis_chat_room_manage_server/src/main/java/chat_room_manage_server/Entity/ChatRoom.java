package chat_room_manage_server.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static chat_room_manage_server.Config.Config.MAX_INFORMATION_LENGTH;

@Data
@Entity
@Table(name = "chat_rooms")
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id", nullable = false)
    private Integer chatRoomId;

    @Column(name = "user_id_1", nullable = false, length = MAX_INFORMATION_LENGTH)
    private String userOneId;

    @Column(name = "user_id_2", nullable = false, length = MAX_INFORMATION_LENGTH)
    private String userTwoId;

    @Column(name = "user_1_leave", nullable = false)
    private Boolean userOneLeft;

    @Column(name = "user_2_leave", nullable = false)
    private Boolean userTwoLeft;

    public ChatRoom(
            final String userOneId,
            final String userTwoId
    ) {
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
        this.userOneLeft = false;
        this.userTwoLeft = false;
    }

}
