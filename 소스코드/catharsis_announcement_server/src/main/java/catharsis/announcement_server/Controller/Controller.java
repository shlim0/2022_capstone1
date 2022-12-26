package catharsis.announcement_server.Controller;

import catharsis.announcement_server.Repository.Repository;
import catharsis.announcement_server.Validation.Validation;
import catharsis.announcement_server.Entity.SystemAlert;
import catharsis.announcement_server.VO.SystemMessage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static catharsis.announcement_server.Config.Config.*;

@RestController
public class Controller {

    private final Repository repository;

    private final Validation validation;

    @Autowired
    public Controller (
            final Repository repository,
            final Validation validation
    ) {
        this.repository = repository;
        this.validation = validation;
    }

    @Transactional
    @PostMapping("/push-system-message")
    public void push_system_message(@RequestBody final SystemMessage systemMessage) throws Exception {
        //유효하지 않은 요청
        if(!validation.message_validation(systemMessage.getSystem_message())) {
            throw new Exception("message validation failure");
        }

        //알림 생성 후 DB로 전달
        SystemAlert systemAlert = repository.insert_system_message(
                new SystemAlert(
                        systemMessage.getSystem_message(),
                        Timestamp.valueOf(LocalDateTime.now())
                )
        );

        //푸시 서버에 알림 전달
        notification_push_system_message_to_push_server(systemAlert);
    }

    private void notification_push_system_message_to_push_server(final SystemAlert systemAlert) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate rt = new RestTemplate();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", systemAlert.getAlert_id());

        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

        ResponseEntity<Void> response = rt.postForEntity(
                "http://" + PUSH_SERVER + "/push-system-message",
                entity,
                Void.class
        );
    }
}
