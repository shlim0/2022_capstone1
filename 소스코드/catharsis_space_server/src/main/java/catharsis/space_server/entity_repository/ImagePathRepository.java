package catharsis.space_server.entity_repository;

import catharsis.space_server.entity.ImagePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImagePathRepository extends JpaRepository<ImagePath, Integer> {
    Optional<ImagePath> findByImagePath(@Param("imagePath") String ImagePath);

    Optional<ImagePath> findById(Integer id);
}
