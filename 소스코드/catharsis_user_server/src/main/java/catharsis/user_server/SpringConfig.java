package catharsis.user_server;

import catharsis.user_server.Manager.ImageManager;
import catharsis.user_server.Repository.*;
import catharsis.user_server.Repository.JpaRepository.*;
import catharsis.user_server.Validation.Validation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;

    private final UserSessionRepository userSessionRepository;

    private final UserLogRepository userLogRepository;

    private final APNsTokenRepository aPNsTokenRepository;

    private final ImagePathRepository imagePathRepository;

    public SpringConfig(UserRepository userRepository,
                        UserSessionRepository userSessionRepository,
                        UserLogRepository userLogRepository,
                        APNsTokenRepository aPNsTokenRepository,
                        ImagePathRepository imagePathRepository
    ) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
        this.userLogRepository = userLogRepository;
        this.aPNsTokenRepository = aPNsTokenRepository;
        this.imagePathRepository = imagePathRepository;
    }

    @Bean
    public Repository repository() { return new Repository();}

    @Bean
    public Validation validation() {
        return new Validation();
    }

    @Bean
    public ImageManager imageManager() { return new ImageManager();}

}
