package catharsis.user_server.Validation;

import catharsis.user_server.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

import static catharsis.user_server.Config.Config.*;

public class Validation {

    private final Repository repository;

    @Autowired
    public Validation(final Repository repository) {
        this.repository = repository;
    }

    //ID 검증
    public boolean user_id_validation(final String user_id) {
        //알파벳 소문자 또는 숫자로 이루어졌으며 길이 제한을 만족하는지의 여부를 반환
        return Pattern.matches("^[a-z0-9]*$", user_id) && user_id.length() <= MAX_INFORMATION_LENGTH;
    }

    //ID의 중복 여부 검증
    public boolean user_id_redundancy_validation(final String user_id) {
        //유저 아이디를 통해 개체가 DB 내에 존재하는지의 여부를 반환
        return repository.is_exist_user_id(user_id);
    }

    //비밀번호 검증
    public boolean user_pwd_validation(final String user_pwd) {
        //알파벳 대소문자 또는 숫자로 이루어졌으며 길이 제한을 만족하는지의 여부를 반환
        return Pattern.matches("^[a-zA-Z0-9]*$", user_pwd) && user_pwd.length() <= MAX_INFORMATION_LENGTH;
    }

    //닉네임 검증
    public boolean nickname_validation(final String nickname) {
        //한글, 알파벳 대소문자 또는 숫자로 이루어졌으며 길이 제한을 만족하는지의 여부를 반환
        return Pattern.matches("^[a-zA-Z0-9가-힣]*$", nickname) && nickname.length() <= MAX_INFORMATION_LENGTH;
    }
    
    //닉네임의 중복 여부 검증
    public boolean nickname_redundancy_validation(final String nickname) {
        //닉네임을 통해 개체가 DB 내에 존재하지 않는지의 여부를 반환
        return repository.is_exist_nickname(nickname);
    }

    //코멘트 검증
    public boolean comment_validation(final String comment) {
        //길이 제한을 만족하는지의 여부를 반환
        return comment.length() <= MAX_DESCRIPTION_LENGTH;
    }

    //로그인 검증
    public boolean login_validation(final String user_id, final String user_pwd) {
        //해당 아이디를 갖는 유저가 존재하고 전달받은 비밀번호와 비밀번호의 일치 여부를 반환
        return user_pwd.equals(repository.get_user_pwd(user_id));
    }

    /* 필요하면 추후에 구현
    public boolean image_validation() {
        return true;
    }
    */

}
