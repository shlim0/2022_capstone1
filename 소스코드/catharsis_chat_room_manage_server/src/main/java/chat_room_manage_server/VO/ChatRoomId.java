package chat_room_manage_server.VO;

import lombok.Data;

@Data
public class ChatRoomId {

    private int chat_room_id;

    public ChatRoomId(final int chat_room_id) {
        this.chat_room_id = chat_room_id;
    }

}
