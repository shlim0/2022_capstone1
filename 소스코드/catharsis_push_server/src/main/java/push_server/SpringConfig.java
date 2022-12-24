package push_server;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.auth.ApnsSigningKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import push_server.Pusher.Pusher;
import push_server.Repository.Repository;

import java.io.FileInputStream;

@Configuration
public class SpringConfig {

    @Bean
    public ApnsClient apnsClient() throws Exception {
        return new ApnsClientBuilder().setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST).setSigningKey(
                        ApnsSigningKey.loadFromInputStream(new FileInputStream(
                                "src/AuthKey_7KK437SC86.p8"),
                                "TTKL6566T6",
                                "7KK437SC86"
                        )
                )
                .build();
    }

    @Bean
    public Pusher pusher() throws Exception {
        return new Pusher(apnsClient());
    }

    @Bean
    public Repository repository() {
        return new Repository();
    }

}
