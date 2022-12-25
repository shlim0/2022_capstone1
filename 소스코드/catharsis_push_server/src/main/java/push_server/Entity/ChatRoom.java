package push_server.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "user_id_1", nullable = false, length = 20)
    private String userOneId;

    @Column(name = "user_id_2", nullable = false, length = 20)
    private String userTwoId;

    @Column(name = "user_1_leave", nullable = false)
    private Boolean userOneLeft;

    @Column(name = "user_2_leave", nullable = false)
    private Boolean userTwoLeft;

}
