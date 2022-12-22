package catharsis.announcement_server.Controller;

import catharsis.announcement_server.Validation.Validation;
import catharsis.announcement_server.VO.SystemMessageID;
import catharsis.announcement_server.Repository.SystemAlertRepository;
import catharsis.announcement_server.Entity.SystemAlert;
import catharsis.announcement_server.VO.SystemMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static catharsis.announcement_server.Config.Config.*;

@RestController
public class Controller {
    @Autowired
    private SystemAlertRepository systemAlertRepository;

    @Autowired
    private WebClient push_server_webClient;

    @Autowired
    private Validation validation;

    @PostMapping("/push-system-message")
    public void push_system_message(@RequestBody final SystemMessage systemMessage) throws Exception {
        //유효하지 않은 요청
        if(!validation.message_validation(systemMessage.getSystem_message())) {
            throw new Exception("message validation failure");
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

    }

    private void notification_push_system_message_to_push_server(final SystemAlert systemAlert) {
        //푸시 서버로 DB에 삽입된 공지의 alert_id를 POST
        push_server_webClient.post()
                .uri("http://" + PUSH_SERVER + "/push-system-message")
                .body(Mono.just(new SystemMessageID(systemAlert.getAlert_id())), SystemMessageID.class)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
