package catharsis.announcement_server.VO;

import lombok.Data;

@Data
public class SystemMessageID {

    private Integer system_message_id;

    public SystemMessageID(Integer system_message_id) {
        this.system_message_id = system_message_id;
    }
}
