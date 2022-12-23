package chat_room_manage_server.VO;

import lombok.Data;

@Data
public class ChatRoomList {

    private Integer chat_room_id_count;

    private Integer[] chat_room_ids;

    public ChatRoomList(final Integer chat_room_id_count, final Integer[] chat_room_ids) {
        this.chat_room_id_count = chat_room_id_count;
        this.chat_room_ids = chat_room_ids;
    }

}
