package catharsis.announcement_server;

import catharsis.announcement_server.Repository.Repository;
import catharsis.announcement_server.Validation.Validation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
