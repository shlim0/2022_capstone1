package catharsis.user_server;

import catharsis.user_server.Manager.ImageManager;
import catharsis.user_server.Repository.*;
import catharsis.user_server.Validation.Validation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public Repository repository() { return new Repository();}

    @Bean
    public Validation validation() {
        return new Validation(repository());
    }

    @Bean
    public ImageManager imageManager() { return new ImageManager();}

}
