package catharsis.space_server.validation;

public interface Validation {
    public boolean session_validation(final int session_id);
    public boolean title_validation(final String title);
    public boolean latitude_validation(final double latitude);
    public boolean longitude_validation(final double longitude);
    public boolean description_validation(final String description);
    public boolean tag_validation(final String tag);
    public boolean space_owner_validation(final int session_id, final int space_id);
}
