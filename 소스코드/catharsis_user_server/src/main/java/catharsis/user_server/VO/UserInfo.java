package catharsis.user_server.VO;

import lombok.Data;

@Data
public class UserInfo {

    private String nickname;

    private String comment;

    private String user_image_address;

    public UserInfo(final String nickname, final String comment, final String user_image_address) {
        this.nickname = nickname;
        this.comment = comment;
        this.user_image_address = user_image_address;
    }

}
