package catharsis.alert_server.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    public List<Favorite> findAllByUser(String user_id);
}
