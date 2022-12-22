package catharsis.user_server.Repository;

import catharsis.user_server.Entity.*;
import catharsis.user_server.Repository.JpaRepository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Repository {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private APNsTokenRepository aPNsTokenRepository;

    @Autowired
    private ImagePathRepository imagePathRepository;

    //해당 아이디와 연결된 모든 토큰 반환
    public List<APNsToken> get_all_APNs_token(final String user_id) {
        return aPNsTokenRepository.findAllByUserId(user_id);
    }

    //토큰에 전달받은 유저 아이디 연결
    public void set_APNs_token(final String aPNs_token, final String user_id) {
        Optional<APNsToken> token = aPNsTokenRepository.findById(aPNs_token);

        //이미 등록되어 있는 토큰이라면 user_id만 설정해준다.
        if(token.isPresent()) {
            token.get().setUserId(user_id);
        }
        else { //토큰을 새로 생성하고 타임스탬프는 현재 시간으로 설정
            aPNsTokenRepository.save(
                    new APNsToken(
                            aPNs_token,
                            user_id,
                            Timestamp.valueOf(LocalDateTime.now())
                    )
            );
        }
    }

    public void unlink_user_id_of_APNs_token(final String aPNs_token) {
        APNsToken token = aPNsTokenRepository.findById(aPNs_token).orElseThrow();
        token.setUserId(null);
    }
    
    //유저 아이디로 개체의 존재 여부를 확인
    public boolean is_exist_user_id(final String user_id) {
        return userRepository.findById(user_id).isPresent();
    }
    
    //닉네임으로 개체의 존재 여부를 확인
    public boolean is_exist_nickname(final String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    //세션 아이디에 대응되는 유저 아이디 얻음
    public String get_user_id_by_session_id(final int session_id) {
        return userSessionRepository.findById(session_id).orElseThrow().getUserId();
    }

    public int insert_image_path(final String image_path) {
        Optional<ImagePath> imagePath = imagePathRepository.findByImagePath(image_path);
        if(imagePath.isPresent()) {
            return imagePath.get().getImagePathId();
        }
        else {
            return imagePathRepository.save(
                    new ImagePath(image_path)
            ).getImagePathId();
        }
    }

    //경로 아이디에 대응되는 경로 얻음
    public String get_image_path(final Integer image_path_id) {
        return imagePathRepository.findById(image_path_id).orElseThrow().getImagePath();
    }

    //유저 아이디로 비밀번호 얻음
    public String get_user_pwd(final String user_id) {
        return userRepository.findById(user_id).orElseThrow().getUserPwd();
    }

    //유저 등록
    public void add_user(final User user) {
        userRepository.save(user);
    }

    //아이디로 유저 정보 얻음
    public User get_user(final String user_id) {
        return userRepository.findById(user_id).orElseThrow();
    }

    //유저 정보 수정
    public void update_user_info(
            final String user_id,
            final String new_user_pwd,
            final String new_nickname,
            final String new_comment,
            final Optional<Integer> image_path_id,
            final Optional<Boolean> withdrawal
    ) {
        User user = userRepository.findById(user_id).orElseThrow();

        if(new_user_pwd != null) {
            user.setUserPwd(new_user_pwd);
        }

        if(new_nickname != null) {
            user.setNickname(new_nickname);
        }

        if(new_comment != null) {
            user.setComment(new_comment);
        }

        if(image_path_id.isPresent()) {
            user.setImagePathId(image_path_id.get());
        }

        if(withdrawal.isPresent()) {
            user.setWithdrawal(withdrawal.get());
        }
    }

    //유저 아이디를 받아 세션을 생성하고 생성된 세션을 반환
    public UserSession create_session(final String user_id) {
        return userSessionRepository.save(
                new UserSession(user_id, Timestamp.valueOf(LocalDateTime.now()))
        );
    }

    //세션 아이디를 통해 세션을 삭제
    public void delete_session(final int session_id) {
        userSessionRepository.deleteById(session_id);
    }

    //아이디를 통해 해당 유저의 모든 세션을 얻음
    public List<UserSession> get_session_list(final String user_id) {
        return userSessionRepository.findAllByUserId(user_id);
    }

    //유저 로그 삽입
    public void insert_user_log(
            final String user_id,
            final int log_status_code,
            final Timestamp timestamp,
            final String ip
    ) {
        userLogRepository.save(
                new UserLog(
                    user_id,
                    log_status_code,
                    timestamp,
                    ip
                )
        );
    }
}
