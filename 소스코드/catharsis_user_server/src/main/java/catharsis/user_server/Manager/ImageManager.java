package catharsis.user_server.Manager;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

    public void save_image(final String full_path, final Object image) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate rt = new RestTemplate();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("image", convertObjectToBytes(image));

        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

        ResponseEntity<Void> response = rt.postForEntity(
                "http://" + IMAGE_SERVER + "/" + full_path,
                entity,
                Void.class
        );
    }

    // object to byte[]
    private static byte[] convertObjectToBytes(final Object obj) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            return boas.toByteArray();
        }
    }
}
