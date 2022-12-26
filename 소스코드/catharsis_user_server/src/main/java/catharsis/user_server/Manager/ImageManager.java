package catharsis.user_server.Manager;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static catharsis.user_server.Config.Config.*;

//ImageManager by 박진혁
public class ImageManager {

    public String get_new_image_path(final String dir) throws Exception {
        URI uri = new URI("http://" + IMAGE_SERVER + "/" + dir + "/image-count");
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.getForEntity(uri, String.class);
        return response.getBody();
    }

    public void save_image(final String full_path, final Object image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate rt = new RestTemplate();
        byte[] img = ((String) image).getBytes();

        HttpEntity<byte[]> entity = new HttpEntity<byte[]>(img, headers);

        ResponseEntity<Void> response = rt.postForEntity(
                "http://" + IMAGE_SERVER + "/" + full_path,
                entity,
                Void.class
        );
    }
}
