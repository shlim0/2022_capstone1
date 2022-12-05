package catharsis.alert_server;

import catharsis.alert_server.VO.Alert;
import catharsis.alert_server.entity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    private FavoriteRepository favoriteRepository;
    private SpaceLogRepository spaceLogRepository;
    private SystemAlertRepository systemAlertRepository;
    private UserSessionRepository userSessionRepository;
    @GetMapping("/alert")
    public List<Alert> getAlertList(@RequestParam("session_id") int session_id, @RequestParam("timestamp") Timestamp timestamp) {
        /* session_id 로 favorite 리스트 구하기 */
        final UserSession user_session = userSessionRepository.findById(session_id);
        final String user_id = user_session.getUser_id();
        final List<Favorite> favorite_list = favoriteRepository.findAllByUser(user_id);
        /* **********************************/

        List<Alert> results = new ArrayList<>();

        /* favorite 등록된 space log를 결과 배열에 추가 */
        for (final Favorite favorite : favorite_list) {
            final int space_id = favorite.getSpace();
            final List<SpaceLog> space_log_list = spaceLogRepository.findAllBySpaceAndTimestampGreaterThanEqual(space_id, timestamp);

            for (SpaceLog space_log : space_log_list) {
                results.add(new Alert(space_log));
            }
        }
        /* ******************************************/

        /* 시스템 공지를 결과 배열에 추가 */
        final List<SystemAlert> system_alert_list = systemAlertRepository.findAllByTimestampGreaterThanEqual(timestamp);
        for (final SystemAlert system_alert : system_alert_list) {
            results.add(new Alert(system_alert));
        }
        /* ***************************/

        return results;
    }
}
