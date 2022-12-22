package catharsis.user_server.VO;

import lombok.Data;

@Data
public class SessionID {

    private Integer session_id;

    public SessionID (final int session_id) {
        this.session_id = session_id;
    }

}
