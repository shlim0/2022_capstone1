package push_server.Repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import push_server.Entity.ChatLog;

public interface ChatLogRepository extends JpaRepository<ChatLog, Integer> {

}
