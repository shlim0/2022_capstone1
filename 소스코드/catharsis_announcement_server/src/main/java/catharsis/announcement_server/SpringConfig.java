package catharsis.announcement_server;

import catharsis.announcement_server.Repository.Repository;
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

import static catharsis.announcement_server.Config.Config.PUSH_SERVER;
import static catharsis.announcement_server.Config.Config.TIME_OUT_MS;

@Configuration
public class SpringConfig {

    @Bean
    public Repository repository() {
        return new Repository();
    }

    @Bean
    public Validation validation() {
        return new Validation();
    }

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIME_OUT_MS)
                .responseTimeout(Duration.ofMillis(TIME_OUT_MS))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(TIME_OUT_MS, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(TIME_OUT_MS, TimeUnit.MILLISECONDS)));

        WebClient webClient = WebClient.builder()
                .baseUrl("http://" + PUSH_SERVER)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("uri", "http://" + PUSH_SERVER))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return webClient;
    }

}
