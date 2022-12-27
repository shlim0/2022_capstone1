package catharsis.space_server;

import catharsis.space_server.repository.Repository;
import catharsis.space_server.repository.RepositoryDefault;
import catharsis.space_server.validation.Validation;
import catharsis.space_server.validation.ValidationDefault;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public ImageManager imageManager() {
        return new ImageManager();
    }

    @Bean
    public Validation validation() {
        return new ValidationDefault();
    }

    @Bean
    public SpaceManager spaceManager() {
        return new SpaceManager(imageManager());
    }

    @Bean
    public Repository repository() {
        return new RepositoryDefault(spaceManager());
    }
}
