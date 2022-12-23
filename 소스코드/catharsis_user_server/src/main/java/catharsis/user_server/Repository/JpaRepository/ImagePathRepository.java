package catharsis.user_server.Repository.JpaRepository;

import catharsis.user_server.Entity.ImagePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImagePathRepository extends JpaRepository<ImagePath, Integer> {

    public Optional<ImagePath> findByImagePath (@Param("imagePath") final String ImagePath);

}
