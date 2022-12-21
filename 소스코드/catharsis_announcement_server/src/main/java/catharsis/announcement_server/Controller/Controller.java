package catharsis.announcement_server.Controller;

import catharsis.announcement_server.Config;
import catharsis.announcement_server.VO.SystemMessageID;
import catharsis.announcement_server.Repository.SystemAlertRepository;
import catharsis.announcement_server.Entity.SystemAlert;
import catharsis.announcement_server.VO.SystemMessage;
import catharsis.announcement_server.Validation;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@RestController
public class Controller {
    @Autowired
    private SystemAlertRepository systemAlertRepository;

    private WebClient webClient = null;

    @PostMapping(Config.POST_PATH)
    @ResponseBody
    public SystemAlert push_system_message(@RequestBody final SystemMessage systemMessage) {
        //유효하지 않은 요청
        if(Validation.message_validation(systemMessage) == false) {
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
        //WebClient 객체가 null일 경우 새로 생성
        if(webClient == null) {
            HttpClient httpClient = HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Config.TIME_OUT_MS)
                    .responseTimeout(Duration.ofMillis(Config.TIME_OUT_MS))
                    .doOnConnected(conn ->
                            conn.addHandlerLast(new ReadTimeoutHandler(Config.TIME_OUT_MS, TimeUnit.MILLISECONDS))
                                    .addHandlerLast(new WriteTimeoutHandler(Config.TIME_OUT_MS, TimeUnit.MILLISECONDS)));

            webClient = WebClient.builder()
                    .baseUrl("http://" + Config.PUSH_SERVER_IP + Config.PUSH_SERVER_PORT)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("uri", "http://" + Config.PUSH_SERVER_IP + Config.PUSH_SERVER_PORT))
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .build();
        }

        //푸시 서버로 DB에 삽입된 공지의 alert_id를 POST
        webClient.post()
                .uri("http://" + Config.PUSH_SERVER_IP + Config.PUSH_SERVER_PORT + Config.POST_PATH)
                .body(Mono.just(new SystemMessageID(systemAlert.getAlert_id())), SystemMessageID.class)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
