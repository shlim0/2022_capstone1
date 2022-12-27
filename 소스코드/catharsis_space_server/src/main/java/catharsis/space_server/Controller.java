package catharsis.space_server;

import catharsis.space_server.entity.Space;
import catharsis.space_server.repository.Repository;
import catharsis.space_server.validation.Validation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    private final double SEARCH_LONGITUDE_EXPAND = 0.003 * 3; // 검색 확장시 경도 범위 증가량. 경도 1도는 약 90km
    private final double SEARCH_LATITUDE_EXPAND = 0.002 * 3; // 검색 확장시 위도 범위 증가량. 위도 1도는 약 130km
    private final int MAX_SEARCH_COUNT = 100; // 이 이상 검색결과가 많으면 확장 중단
    private final int MAX_SEARCH_EXPAND = 3; // 검색 범위 최대 확장 수
    
    private ImageManager imageManager;
    private Repository repository;
    private Validation validation;

    public Controller(ImageManager imageManager, Repository repository, Validation validation) {
        this.imageManager = imageManager;
        this.repository = repository;
        this.validation = validation;
    }

    // 장소 등록
    @PostMapping("/space")
    public Map<String, Object> add_space(@RequestBody Map<String, Object> params) throws Exception {
        if (validation.session_validation((Integer)params.get("session_id")) == false) {
            throw new Exception("session validation false");
        }

        if (validation.title_validation((String)params.get("title")) == false) {
            throw new Exception("title validation false");
        }

        if (validation.longitude_validation((Double)params.get("longitude")) == false) {
            throw new Exception("longitude validation false");
        }

        if (validation.latitude_validation((Double)params.get("latitude")) == false) {
            throw new Exception("latitude validation false");
        }

        if (validation.description_validation((String)params.get("discription")) == false) {
            throw new Exception("description validation false");
        }

        if ((Integer)params.get("tag_count") > 0) {
            final List<String> tags = (List<String>)params.get("tags");
            for (final String tag : tags) {
                if (validation.tag_validation(tag) == false) {
                    throw new Exception("tag validation false");
                }
            }
        }

        final int space_id = repository.create_space(params);

        Map<String, Object> result = new HashMap<>();
        result.put("space_id", space_id);
        return result;
    }

    // 장소 수정
    @PatchMapping("/space")
    public Map<String, Object> patch_space(@RequestBody Map<String, Object> params) throws Exception {
        if (validation.session_validation((Integer)params.get("session_id")) == false) {
            throw new Exception("session validation false");
        }

        if (validation.space_owner_validation((Integer)params.get("session_id"), (Integer)params.get("space_id")) == false) {
            throw new Exception("space owner validation false");
        }

        if (params.containsKey("title") && validation.title_validation((String)params.get("title")) == false) {
            throw new Exception("title validation false");
        }

        if (params.containsKey("longitude") && validation.longitude_validation((Double)params.get("longitude")) == false) {
            throw new Exception("longitude validation false");
        }

        if (params.containsKey("latitude") && validation.latitude_validation((Double)params.get("latitude")) == false) {
            throw new Exception("latitude validation false");
        }

        if (params.containsKey("description") && validation.description_validation((String)params.get("description")) == false) {
            throw new Exception("description validation false");
        }

        if (params.containsKey("tag_count") && (Integer)params.get("tag_count") > 0) {
            final List<String> tags = (List<String>)params.get("tags");
            for (final String tag : tags) {
                if (validation.tag_validation(tag) == false) {
                    throw new Exception("tag validation false");
                }
            }
        }

        final int space_id = repository.update_space(params);

        Map<String, Object> result = new HashMap<>();
        result.put("space_id", space_id);
        return result;
    }

    // 장소 삭제
    @DeleteMapping("/space")
    public void delete_space(@RequestParam(name="session_id") int session_id, @RequestParam(name="space_id") int space_id) throws Exception {
        if (validation.session_validation(session_id) == false) {
            throw new Exception("session validation false");
        }

        if (validation.space_owner_validation(session_id, space_id) == false) {
            throw new Exception("space owner validation false");
        }

        repository.delete_space(space_id);
    }

    // 특정 사용자가 업로드한 장소 조회
    @GetMapping("/user/space-list")
    public Map<String, Object> get_space_list(@RequestParam(name="user_id") String user_id) {
        Map<String, Object> result = new HashMap<>();
        final List<Space> space_list = repository.get_user_space_list(user_id);
        result.put("space_count", space_list.size());
        result.put("spaces", space_list);
        return result;
    }

    // 장소 상세 정보 조회
    @GetMapping("/space")
    public Map<String, Object> get_space_data(@RequestParam(name="space_id") int space_id) {
        return repository.get_space_data(space_id);
    }

    // 장소 검색
    @PostMapping("/search")
    public Map<String, Object> search(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        List<Integer> spaces = new ArrayList<>();

        final double mid_latitude = (Double)params.get("latitude");
        final double mid_longitude = (Double)params.get("longitude");
        List<String> tags = null;
        if (params.containsKey("tag")) {
            tags = new ArrayList<>();
            tags.add((String)params.get("tag"));
        }

        /* 범위를 확장시키면서 검색 */
        for (int i = 1; i <= MAX_SEARCH_EXPAND; ++i) {
            if (spaces.size() > MAX_SEARCH_COUNT) {
                break;
            }

            /* 위/경도 범위 설정 */
            final double begin_latitude = mid_latitude - (i * SEARCH_LATITUDE_EXPAND);
            final double end_latitude = mid_latitude + (i * SEARCH_LATITUDE_EXPAND);
            final double begin_longitude = mid_longitude - (i * SEARCH_LATITUDE_EXPAND);
            final double end_longitude = mid_longitude + (i * SEARCH_LONGITUDE_EXPAND);
            /* ***************/
            final List<Space> space_list = repository.search_space(begin_latitude, end_latitude, begin_longitude, end_longitude, tags);

            spaces = new ArrayList<>();
            for (final Space space : space_list) {
                spaces.add(space.getId());
            }
        }
        /* *********************/

        result.put("space_id_count", spaces.size());
        result.put("spaces", spaces);
        return result;
    }

    // 관심 목록 조회
    @GetMapping("/user/favorite-spaces")
    public Map<String, Object> get_favorite_spaces(@RequestParam String user_id) {
        Map<String, Object> result = new HashMap<>();

        final List<Integer> favorite_space_list = repository.get_favorite_space_id_list(user_id);
        result.put("favorite_count", favorite_space_list.size());
        result.put("spaces", favorite_space_list);

        return result;
    }

    // 관심 목록 등록
    @PostMapping("/user/favorite-spaces")
    public void add_favorite_space(@RequestBody Map<String, Object> params) throws Exception {
        final int session_id = (Integer)params.get("session_id");
        final int space_id = (Integer)params.get("space_id");

        if (validation.session_validation(session_id) == false) {
            throw new Exception("session validation false");
        }

        repository.add_favorite_space(session_id, space_id);
    }

    // 관심 목록 삭제
    @DeleteMapping("/user/favorite-spaces")
    public void remove_favorite_space(@RequestParam(name="session_id") int session_id, @RequestParam(name="space_id") int space_id) {
        repository.delete_favorite_space(session_id, space_id);
    }
}
