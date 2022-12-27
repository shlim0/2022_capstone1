package catharsis.space_server.repository;

import catharsis.space_server.SpaceManager;
import catharsis.space_server.entity.*;
import catharsis.space_server.entity_repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryDefault implements Repository {
    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private SpaceTagRepository spaceTagRepository;

    @Autowired
    private SpaceImageRepository spaceImageRepository;

    @Autowired
    private SpaceLogRepository spaceLogRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ImagePathRepository imagePathRepository;

    private SpaceManager spaceManager;

    public RepositoryDefault(SpaceManager spaceManager) {
        this.spaceManager = spaceManager;
    }

    // 태그가 존재하는지 검사
    @Override
    public boolean is_exist_tag(final String tag) {
        return tagsRepository.findByTag(tag).isEmpty() == false;
    }

    // 태그 추가. 이미 존재하는 태그면 아무일도 일어나지 않음
    @Override
    public void add_tag(final String tag) {
        if (is_exist_tag(tag) == false) {
            tagsRepository.save(new Tag(tag));
        }
    }

    // 태그로 태그 id 얻기
    @Override
    public int get_tag_id(final String tag) {
        return tagsRepository.findByTag(tag).get().getId();
    }

    // 새 장소 등록.
    @Override
    @Transactional
    public int create_space(final Map<String, Object> space) throws Exception {
        final int session_id = (Integer)space.get("session_id");
        final String user_id = userSessionRepository.findById(session_id).get().getUserId();

        /* 장소 엔티티 생성 */
        Space space_entity = new Space();
        space_entity.setUserId(user_id);
        space_entity.setTitle((String)space.get("title"));
        space_entity.setLatitude((Double)space.get("latitude"));
        space_entity.setLongitude((Double)space.get("longitude"));

        if (space.containsKey("description")) {
            space_entity.setDescription((String)space.get("description"));
        }

        space_entity.setStatus(0);
        /* *****************/

        /* space 테이블 insert */
        final int space_id = spaceRepository.save(space_entity).getId();
        /* ********************/

        if ((Integer)space.get("tag_count") > 0) {
            final List<Integer> tag_id_list = spaceManager.change_tag_list_to_tag_id_list((List<String>)space.get("tags")); // tags 테이블 insert

            /* space_tag 테이블 insert */
            for (final int tag_id : tag_id_list) {
                spaceTagRepository.save(new SpaceTag(space_id, tag_id));
            }
            /* ************************/
        }

        if ((Integer)space.get("image_count") > 0) {
            final List<Integer> image_id_list = spaceManager.change_image_list_to_image_id_list(user_id, (List<Object>)space.get("images")); // image_path 테이블 insert
            /* space_image 테이블 insert */
            for (final int image_path_id : image_id_list) {
                spaceImageRepository.save(new SpaceImage(space_id, image_path_id));
            }
            /* *************************/
        }

        /* 장소 로그 테이블 추가 */
        spaceLogRepository.save(new SpaceLog(space_id, 0));
        /* *******************/

        return space_id;
    }

    @Override
    @Transactional
    public int update_space(final Map<String, Object> space) throws Exception {
        final int session_id = (Integer)space.get("session_id");
        final String user_id = userSessionRepository.findById(session_id).get().getUserId();
        final int space_id = (Integer)space.get("space_id");

        /* 장소 엔티티 생성 */
        Space space_entity = new Space();
        space_entity.setId(space_id);
        space_entity.setUserId(user_id);

        if (space.containsKey("title")) {
            space_entity.setTitle((String)space.get("title"));
            spaceLogRepository.save(new SpaceLog(space_id, 4)); // 타이틀 변경 로그
        }

        boolean position_change = false;
        if (space.containsKey("latitude")) {
            space_entity.setLatitude((Double)space.get("latitude"));
            position_change = true;
        }

        if (space.containsKey("longitude")) {
            space_entity.setLongitude((Double)space.get("longitude"));
            position_change = true;
        }

        if (position_change) {
            spaceLogRepository.save(new SpaceLog(space_id, 5)); // 위치 변경 로그
        }

        if (space.containsKey("description")) {
            space_entity.setDescription((String)space.get("description"));
            spaceLogRepository.save(new SpaceLog(space_id, 6)); // 장소 설명 변경 로그
        }

        if (space.containsKey("status")) {
            space_entity.setStatus((Integer)space.get("status"));

            if ((Integer)space.get("status") == 0) {
                spaceLogRepository.save(new SpaceLog(space_id, 0)); // 상태 로그
            }
            else {
                spaceLogRepository.save(new SpaceLog(space_id, 1)); // 상태 변경 로그
            }
        }
        /* *****************/

        if (space.containsKey("tag_count")) {
            final List<Integer> tag_id_list = spaceManager.change_tag_list_to_tag_id_list((List<String>)space.get("tags")); // tags 테이블 insert

            spaceTagRepository.deleteAllBySpaceId(space_id); // 기존 태그들 연결관계 삭제

            /* space_tag 테이블 insert */
            for (final int tag_id : tag_id_list) {
                spaceTagRepository.save(new SpaceTag(space_id, tag_id));
            }
            /* ************************/

            spaceLogRepository.save(new SpaceLog(space_id, 7)); // 태그 변경 로그
        }

        if (space.containsKey("image_count")) {
            final List<Integer> image_id_list = spaceManager.change_image_list_to_image_id_list(user_id, (List<Object>)space.get("images")); // image_path 테이블 insert

            spaceImageRepository.deleteAllBySpaceId(space_id); // 기존 이미지 연결관계 삭제

            /* space_image 테이블 insert */
            for (final int image_path_id : image_id_list) {
                spaceImageRepository.save(new SpaceImage(space_id, image_path_id));
            }
            /* *************************/

            spaceLogRepository.save(new SpaceLog(space_id, 3)); // 이미지 변경 로그
        }

        return space_id;
    }

    @Override
    public boolean is_exist_space_id(final int space_id) {
        return spaceRepository.findById(space_id).isEmpty() == false;
    }

    @Override
    @Transactional
    public void delete_space(final int space_id) {
        /* space 테이블 status 2(삭제됨)로 변경 */
        Space space = spaceRepository.findById(space_id).get();
        space.setStatus(2);
        spaceRepository.save(space);
        /* *********************************/

        spaceLogRepository.save(new SpaceLog(space_id, 2)); // space_log 테이블에 로깅
    }

    @Override
    public List<Space> get_user_space_list(final String user_id) {
        return spaceRepository.findAllByUserId(user_id);
    }

    @Override
    @Transactional
    public Map<String, Object> get_space_data(final int space_id) {
        final Space space = spaceRepository.findById(space_id).get();
        Map<String, Object> result = new HashMap<>();
        result.put("user_id", space.getUserId());
        result.put("status", space.getStatus());
        result.put("title", space.getTitle());
        result.put("latitude", space.getLatitude());
        result.put("longitude", space.getLongitude());
        result.put("description", space.getDescription());

        /* tag 정보 join */
        List<String> tags = new ArrayList<>();
        final List<SpaceTag> space_tags = spaceTagRepository.findAllBySpaceId(space_id);
        for (final SpaceTag space_tag : space_tags) {
            final Tag tag = tagsRepository.findById(space_tag.getTagId()).get();
            tags.add(tag.getTag());
        }

        result.put("tag_count", tags.size());
        result.put("tags", tags);
        /* ***************/

        /* 이미지 경로 join */
        List<String> image_paths = new ArrayList<>();
        final List<SpaceImage> space_image_list = spaceImageRepository.findAllBySpaceId(space_id);
        for (final SpaceImage space_image : space_image_list) {
            final ImagePath image_path = imagePathRepository.findById(space_image.getId()).get();
            image_paths.add(image_path.getImagePath());
        }

        result.put("image_count", image_paths.size());
        result.put("images", image_paths);
        /* *****************/

        return result;
    }

    @Override
    public List<Space> search_space(final double begin_latitude, final double end_latitude, final double begin_longitude, final double end_longitude, final List<String> tags) {
        List<Space> result = new ArrayList<>();

        final List<Space> candidates = spaceRepository.findAllByLatitudeGreaterThanEqualAndLatitudeLessThanEqualAndLongitudeGreaterThanEqualAndLongitudeLessThanEqual(begin_latitude, end_latitude, begin_longitude, end_longitude);
        if (tags == null) {
            return candidates;
        }

        /* 태그가 전부 있는지 검사 */
        final List<Integer> tag_id_list = spaceManager.change_tag_list_to_tag_id_list(tags);
        for (final Space space : candidates) {
            if (has_all_tag(space.getId(), tag_id_list)) {
                result.add(space);
            }
        }
        /* ***********************/

        return result;
    }

    @Override
    public List<Integer> get_favorite_space_id_list(final String user_id) {
        final List<Favorite> favorites = favoriteRepository.findAllByUserId(user_id);

        List<Integer> result = new ArrayList<>();
        for (final Favorite favorite : favorites) {
            result.add(favorite.getId());
        }

        return result;
    }

    @Override
    public void add_favorite_space(final int session_id, final int space_id) {
        final String user_id = userSessionRepository.findById(session_id).get().getUserId();
        favoriteRepository.save(new Favorite(user_id, space_id));
    }

    @Override
    public void delete_favorite_space(final int session_id, final int space_id) {
        final String user_id = userSessionRepository.findById(session_id).get().getUserId();
        favoriteRepository.deleteByUserIdAndSpaceId(user_id, space_id);
    }

    private boolean has_tag(final int space_id, final int tag_id) {
        return spaceTagRepository.findBySpaceIdAndTagId(space_id, tag_id).isEmpty() == false;
    }

    private boolean has_all_tag(final int space_id, final List<Integer> tag_ids) {
        for (int tag_id : tag_ids) {
            if (spaceTagRepository.findBySpaceIdAndTagId(space_id, tag_id).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
