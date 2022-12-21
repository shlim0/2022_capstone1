package catharsis.announcement_server.Controller;

import catharsis.announcement_server.Config.Config;
import catharsis.announcement_server.VO.SystemMessageID;
import catharsis.announcement_server.Repository.SystemAlertRepository;
import catharsis.announcement_server.Entity.SystemAlert;
import catharsis.announcement_server.VO.SystemMessage;
import catharsis.announcement_server.Validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
public class Controller {
    @Autowired
    private SystemAlertRepository systemAlertRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private WebClient push_server_webClient;

    @PostMapping(Config.POST_PATH)
    @ResponseBody
    public SystemAlert push_system_message(@RequestBody final SystemMessage systemMessage) {
        //유효하지 않은 요청
        if(validation.message_validation(systemMessage) == false) {
            return null;
        }

        //알림 생성 후 DB로 전달
        SystemAlert systemAlert = systemAlertRepository.save(
                new SystemAlert(
                        systemMessage.getSystem_message(),
                        Timestamp.valueOf(LocalDateTime.now())
                )
        );

        //푸시 서버에 알림 전달
        notification_push_system_message_to_push_server(systemAlert);

        return systemAlert;
    }

    private void notification_push_system_message_to_push_server(final SystemAlert systemAlert) {
        //푸시 서버로 DB에 삽입된 공지의 alert_id를 POST
        push_server_webClient.post()
                .uri("http://" + Config.PUSH_SERVER_IP + Config.PUSH_SERVER_PORT + Config.POST_PATH)
                .body(Mono.just(new SystemMessageID(systemAlert.getAlert_id())), SystemMessageID.class)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
