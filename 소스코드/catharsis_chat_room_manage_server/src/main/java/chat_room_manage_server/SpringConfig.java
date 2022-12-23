package chat_room_manage_server;

import chat_room_manage_server.Repository.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public Repository repository() {
        return new Repository();
    }

}
