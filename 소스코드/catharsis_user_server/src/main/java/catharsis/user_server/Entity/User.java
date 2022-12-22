package catharsis.user_server.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Optional;

import static catharsis.user_server.Config.Config.*;

@Data
@Entity
@Table(name = "user")
public class User {

        @Id
        @Column(name = "user_id", nullable = false, length = MAX_INFORMATION_LENGTH)
        private String userId;

        @Column(name = "user_pwd", nullable = false, length = MAX_INFORMATION_LENGTH)
        private String userPwd;

        @Column(name = "nickname", nullable = false, length = MAX_INFORMATION_LENGTH)
        private String nickname;

        @Column(name = "comment", length = MAX_DESCRIPTION_LENGTH)
        private String comment;

        @Column(name = "withdrawal", nullable = false)
        private Boolean withdrawal;

        @Column(name = "image_path_id")
        private Integer imagePathId;

        public User (
                final String user_id,
                final String user_pwd,
                final String nickname,
                final String comment,
                final Integer image_path_id,
                final Boolean withdrawal
        ) {
                this.userId = user_id;
                this.userPwd = user_pwd;
                this.nickname = nickname;
                this.comment = comment;
                this.imagePathId = image_path_id;
                this.withdrawal = withdrawal;
        }

}
