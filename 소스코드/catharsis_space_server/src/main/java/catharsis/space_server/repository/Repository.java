package catharsis.space_server.repository;

import catharsis.space_server.entity.Space;

import java.util.List;
import java.util.Map;

public interface Repository {
    public boolean is_exist_tag(final String tag);
    public void add_tag(final String tag);
    public int get_tag_id(final String tag);
    public int create_space(final Map<String, Object> space) throws Exception;
    public int update_space(final Map<String, Object> space) throws Exception;
    public boolean is_exist_space_id(final int space_id);
    public void delete_space(final int space_id);
    public List<Space> get_user_space_list(final String user_id);
    public Map<String, Object> get_space_data(final int space_id);
    public List<Space> search_space(final double begin_latitude, final double end_latitude, final double begin_longitude, final double end_longitude, final List<String> tags);
    public List<Integer> get_favorite_space_id_list(final String user_id);
    public void add_favorite_space(final int session_id, final int space_id);
    public void delete_favorite_space(final int session_id, final int space_id);
}
