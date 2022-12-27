package catharsis.space_server;

import catharsis.space_server.entity.ImagePath;
import catharsis.space_server.entity.Tag;
import catharsis.space_server.entity_repository.ImagePathRepository;
import catharsis.space_server.entity_repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpaceManager {
    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private ImagePathRepository imagePathRepository;
    private ImageManager imageManager;

    public SpaceManager(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    // 태그 목록을 데이터베이스에 삽입 후 그 id 리스트를 반환
    @Transactional
    public List<Integer> change_tag_list_to_tag_id_list(final List<String> tag_list) {
        List<Integer> result = new ArrayList<>();
        for (final String tag : tag_list) {
            final Optional<Tag> op_tag = tagsRepository.findByTag(tag);
            if (op_tag.isEmpty()) { // tags 테이블에 없으면 삽입 후 id 반환
                result.add(tagsRepository.save(new Tag(tag)).getId());
            }
            else { // tags 테이블에 있으면 그 id 사용
                result.add(op_tag.get().getId());
            }
        }

        return result;
    }

    // 이미지를 이미지 서버에 저장 -> image path 테이블에 저장 후 그 id 반환
    @Transactional
    public List<Integer> change_image_list_to_image_id_list(final String user_id, final List<Object> image_list) throws Exception {
        List<Integer> result = new ArrayList<>();
        for (final Object image : image_list) {
            /* 이미지 서버에 저장 */
            final String path = imageManager.get_new_image_path(user_id);
            imageManager.save_image(user_id + "/" + path, image);
            /* *****************/

            final int image_path_id = imagePathRepository.save(new ImagePath(user_id + "/" + path)).getId();
            result.add(image_path_id);

            //Optional<ImagePath> image_path_opt = imagePathRepository.findByImagePath(path);
            //if (image_path_opt.isEmpty()) { // image_path 테이블에 없으면 추가 후 id 반환
            //    final int image_path_id = imagePathRepository.save(new ImagePath(path)).getId();
            //    result.add(image_path_id);
            //}
            //else { // image_path 테이블에 있으면 id 반환
            //    result.add(image_path_opt.get().getId());
            //}
        }

        return result;
    }
}
