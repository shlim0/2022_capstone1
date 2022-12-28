package catharsis.user_server.Controller;

import catharsis.user_server.Entity.APNsToken;
import catharsis.user_server.Entity.User;
import catharsis.user_server.Entity.UserSession;
import catharsis.user_server.Manager.ImageManager;
import catharsis.user_server.Repository.*;
import catharsis.user_server.VO.SessionID;
import catharsis.user_server.VO.UserInfo;
import catharsis.user_server.Validation.Validation;

import jakarta.servlet.http.HttpServletRequest;


import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
public class Controller {

    private final Repository repository;

    private final Validation validation;

    private final ImageManager imageManager;

    @Autowired
    public Controller (
            final Repository repository,
            final Validation validation,
            final ImageManager imageManager
    ) {
        this.repository = repository;
        this.validation = validation;
        this.imageManager = imageManager;
    }

    //회원가입
    @Transactional
    @PostMapping("/user")
    public ResponseEntity registration(final HttpServletRequest httpServletRequest) throws Exception {
        final Map<String, Object> obj = requestParser(httpServletRequest);
        final String user_id = (String) obj.get("user_id");

        //아이디 검증
        if(!validation.user_id_validation(user_id)) {
            //302
            return ResponseEntity.status(HttpStatus.FOUND).build();
        }

        //아이디 존재 여부 확인
        if(validation.user_id_redundancy_validation(user_id)) {
            //300
            return ResponseEntity.status(HttpStatus.MULTIPLE_CHOICES).build();
        }

        //비밀번호 검증
        final String user_pwd = (String) obj.get("user_pwd");
        if(!validation.user_pwd_validation(user_pwd)) {
            //303
            return ResponseEntity.status(HttpStatus.SEE_OTHER).build();
        }

        //닉네임 검증
        final String nickname = (String) obj.get("nickname");
        if(!validation.nickname_validation(nickname)) {
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        //닉네임 존재 여부 확인
        if(validation.nickname_redundancy_validation(nickname)) {
            //301
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).build();
        }

        //자기소개 검증
        final String comment = (String) obj.get("comment");
        if(comment != null && !validation.comment_validation(comment)) {
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        //이미지가 있으면 이미지 서버에 저장하고 이미지 경로 데이터베이스에 저장
        final Object image = obj.get("user_image");
        String image_path = null;
        Integer image_path_id = null;
        if(image != null) {
            image_path = imageManager.get_new_image_path(user_id);
            imageManager.save_image(image_path, image);
            image_path_id = repository.insert_image_path(image_path);
        }

        final User user = new User(
                user_id,
                user_pwd,
                nickname,
                comment,
                image_path_id,
                false
        );
        repository.add_user(user);

        final String user_ip = getClientIP(httpServletRequest);
        //회원가입 로그 작성
        repository.insert_user_log(
                user_id,
                0,
                Timestamp.valueOf(LocalDateTime.now()),
                user_ip
        );

        //200
        return ResponseEntity.ok().build();
    }

    //회원정보 조회
    @Transactional
    @GetMapping("/user")
    public UserInfo get_user_info(@RequestParam (name = "user_id") final String user_id) {
            final User user = repository.get_user(user_id);

            //해당 사용자의 닉네임, 코멘트, 프로필 사진의 경로를 반환
            return new UserInfo(
              user.getNickname(),
              user.getComment(),
              user.getImagePathId() == null ? null : repository.get_image_path(user.getImagePathId())
            );
    }

    //회원정보 수정
    @Transactional
    @PatchMapping("/user")
    public void update_user_info(final HttpServletRequest httpServletRequest) throws Exception {
        int update_check = 0;
        final Map<String, Object> obj = requestParser(httpServletRequest);
        final int session_id = (Integer) obj.get("session_id");
        final String user_id = repository.get_user_id_by_session_id(session_id);
        final String user_pwd = (String) obj.get("user_pwd");

        //로그인 검증
        if(!validation.login_validation(user_id, user_pwd)) {
            throw new Exception("인증 실패");
        }

        //null이면 변경하지 않는 경우에 해당함
        final String new_user_pwd = (String) obj.get("new_user_pwd");
        if(new_user_pwd != null) {
            if(!validation.user_pwd_validation(new_user_pwd)) {
                throw new Exception("유효하지 않은 비밀번호입니다.");
            }
            update_check |= (1 << 5);
        }

        //null이면 변경하지 않는 경우에 해당함
        final String new_nickname = (String) obj.get("nickname");
        if(new_nickname != null) {
            if(!validation.user_pwd_validation(new_user_pwd)) {
                throw new Exception("유효하지 않은 비밀번호입니다.");
            }
            if(validation.nickname_redundancy_validation(new_nickname)) {
                throw new Exception("이미 존재하는 닉네임입니다.");
            }
            update_check |= (1 << 6);
        }

        //null이면 변경하지 않는 경우에 해당함
        final String new_comment = (String) obj.get("comment");
        if(new_comment != null) {
            if(!validation.comment_validation(new_comment)) {
                throw new Exception("자기소개가 너무 깁니다.");
            }
            update_check |= (1 << 7);
        }

        //null이면 변경하지 않는 경우에 해당함
        final Object image = obj.get("user_image");
        String image_path = null;
        Optional<Integer> image_path_id = null;
        if(image != null) {
            image_path = imageManager.get_new_image_path(user_id);
            imageManager.save_image(image_path, image);
            image_path_id = Optional.of(repository.insert_image_path(image_path));
            update_check |= (1 << 8);
        }

        repository.update_user_info(
                user_id,
                new_user_pwd,
                new_nickname,
                new_comment,
                image_path_id,
                Optional.of(false)
        );

        final String user_ip = getClientIP(httpServletRequest);
        final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        //비트마스킹으로 갱신된 항목을 확인하고 각각 로그 삽입
        for(int i = 5; i <= 8; ++i) {
            if((update_check & (1 << i)) == (1 << i)) {
                repository.insert_user_log(
                        user_id,
                        i,
                        timestamp,
                        user_ip
                );
            }
        }
    }

    //회원탈퇴
    @Transactional
    @DeleteMapping("/user")
    public void unregistration(final HttpServletRequest httpServletRequest) throws Exception {
        final int session_id = httpServletRequest.getIntHeader("session_id");
        final String user_id = repository.get_user_id_by_session_id(session_id);
        final String user_pwd = httpServletRequest.getHeader("user_pwd");
        final String user_ip = getClientIP(httpServletRequest);
        
        if(!validation.login_validation(user_id, user_pwd)) {
            throw new Exception("인증 실패");
        }

        //유저의 모든 세션 검색
        List<UserSession> session_list = repository.get_session_list(user_id);

        //탈퇴여부 비트 갱신
        repository.update_user_info(
                user_id,
                null,
                null,
                null,
                null,
                Optional.of(true)
        );

        List<APNsToken> token_list = repository.get_all_APNs_token(user_id);
        
        //토큰과 계정 연결 해제
        for(APNsToken token : token_list) {
            repository.unlink_APNs_token(token.getId());
        }

        //세션 삭제
        for(UserSession session : session_list) {
            repository.delete_session(session.getSessionId());
        }

        //회원탈퇴 로그 작성
        repository.insert_user_log(
                user_id,
                1,
                Timestamp.valueOf(LocalDateTime.now()),
                user_ip
        );
    }

    //로그인
    @Transactional
    @PostMapping("/session")
    public ResponseEntity<SessionID> login(final HttpServletRequest httpServletRequest) throws Exception {
        final Map<String, Object> obj = requestParser(httpServletRequest);
        final String user_id = (String) obj.get("user_id");
        final String user_pwd = (String) obj.get("user_pwd");
        final String aPNs_token = (String) obj.get("APNs_token");
        final String user_ip = getClientIP(httpServletRequest);

        //로그인 검증, 검증에 실패하면 에러 발생
        if(!validation.login_validation(user_id, user_pwd)) {
            //300
            return new ResponseEntity<>(HttpStatus.MULTIPLE_CHOICES);
        }

        //이미 연결된 토큰으로 접속을 시도할 경우 상태코드 301 반환
        if(validation.is_linked_token(aPNs_token)) {
            //301
            return new ResponseEntity<>(HttpStatus.MOVED_PERMANENTLY);
        }

        //세션 생성 후 반환
        final UserSession session = repository.create_session(user_id);
        
        //토큰과 유저 아이디 연결
        set_APNs_token(aPNs_token, session.getUserId());

        //로그인 로그 작성
        repository.insert_user_log(
                user_id,
                2,
                Timestamp.valueOf(LocalDateTime.now()),
                user_ip
        );

        //200
        return ResponseEntity.ok(new SessionID(session.getSessionId()));
    }

    //로그아웃
    @Transactional
    @DeleteMapping("/session")
    public void logout(final HttpServletRequest httpServletRequest) {
        final int session_id = httpServletRequest.getIntHeader("session_id");
        final String aPNs_token = httpServletRequest.getHeader("APNs_token");
        final String user_id = repository.get_user_id_by_session_id(session_id);
        final String user_ip = getClientIP(httpServletRequest);

        //토근에 연결된 유저 아이디 제거
        repository.unlink_APNs_token(aPNs_token);

        //세션을 데이터베이스에서 삭제
        repository.delete_session(session_id);
        
        //로그아웃 로그 작성
        repository.insert_user_log(
                user_id,
                3,
                Timestamp.valueOf(LocalDateTime.now()),
                user_ip
        );
    }

    //토큰과 유저 아이디를 연결시킴
    private void set_APNs_token(final String aPNs_token, final String user_id) {
        repository.set_APNs_token(aPNs_token, user_id);
    }

    //codes from https://linked2ev.github.io/java/2019/05/22/JAVA-1.-java-get-clientIP/
    private String getClientIP(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    private Map<String, Object> requestParser (final HttpServletRequest request) throws Exception {
        StringBuffer jb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }

        JSONParser parser = new JSONParser(jb.toString());
        return (Map<String, Object>) parser.parse();
    }
}

