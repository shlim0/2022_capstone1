package catharsis.space_server.validation;

import catharsis.space_server.entity.Space;
import catharsis.space_server.entity.UserSession;
import catharsis.space_server.entity_repository.SpaceRepository;
import catharsis.space_server.entity_repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ValidationDefault implements Validation {
    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private SpaceRepository spaceRepository;
    
    // 세션 id가 user_session 테이블에 존재하는지 검사
    @Override
    public boolean session_validation(final int session_id) {
        return userSessionRepository.findById(session_id).isEmpty() == false;
    }

    @Override
    public boolean title_validation(final String title) {
        if (title == null) {
            return false;
        }

        if (title.length() > 100) {
            return false;
        }

        return true;
    }

    @Override
    public boolean latitude_validation(final double latitude) {
        if (latitude < -90 || 90 < latitude) {
            return false;
        }

        return true;
    }

    @Override
    public boolean longitude_validation(final double longitude) {
        if (longitude < -180 || 180 < longitude) {
            return false;
        }

        return true;
    }

    @Override
    public boolean description_validation(final String description) {
        if (description != null && description.length() > 1000) {
            return false;
        }

        return true;
    }

    @Override
    public boolean tag_validation(final String tag) {
        if (tag.length() > 100) {
            return false;
        }

        return true;
    }

    @Override
    public boolean space_owner_validation(final int session_id, final int space_id) {
        final Optional<Space> space = spaceRepository.findById(space_id);
        if (space.isEmpty()) {
            return false;
        }

        final Optional<UserSession> userSession = userSessionRepository.findById(session_id);
        if (userSession.isEmpty()) {
            return false;
        }

        if (space.get().getUserId().equals(userSession.get().getUserId()) == false) {
            return false;
        }

        return true;
    }
}
