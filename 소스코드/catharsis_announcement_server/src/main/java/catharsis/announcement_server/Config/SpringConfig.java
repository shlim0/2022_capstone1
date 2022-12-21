package catharsis.announcement_server.Config;

import catharsis.announcement_server.Repository.SystemAlertRepository;
import catharsis.announcement_server.Validation.Validation;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Configuration
public class SpringConfig {
    private final SystemAlertRepository systemAlertRepository;

    public SpringConfig(SystemAlertRepository systemAlertRepository) {
        this.systemAlertRepository = systemAlertRepository;
    }

    @Bean
    public Validation validation() {
        return new Validation();
    }

    @Bean
    public WebClient webclient() {
            HttpClient httpClient = HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Config.TIME_OUT_MS)
                    .responseTimeout(Duration.ofMillis(Config.TIME_OUT_MS))
                    .doOnConnected(conn ->
                            conn.addHandlerLast(new ReadTimeoutHandler(Config.TIME_OUT_MS, TimeUnit.MILLISECONDS))
                                    .addHandlerLast(new WriteTimeoutHandler(Config.TIME_OUT_MS, TimeUnit.MILLISECONDS)));

            WebClient webClient = WebClient.builder()
                    .baseUrl("http://" + Config.PUSH_SERVER_IP + Config.PUSH_SERVER_PORT)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("uri", "http://" + Config.PUSH_SERVER_IP + Config.PUSH_SERVER_PORT))
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .build();

            return webClient;
    }
}
