package push_server.Repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import push_server.Entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

}
