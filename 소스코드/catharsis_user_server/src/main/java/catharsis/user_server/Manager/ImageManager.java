package catharsis.user_server.Manager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;

import static catharsis.user_server.Config.Config.*;

//ImageManager by 박진혁
public class ImageManager {

    public String get_new_image_path(final String dir) throws Exception {
        URL url = new URL(Paths.get(IMAGE_SERVER, dir, "image-count").toString());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        return conn.getResponseMessage();
    }

    public void save_image(final String full_path, final Object image) throws Exception {
        URL url = new URL(Paths.get(IMAGE_SERVER, full_path).toString());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        conn.setRequestMethod("POST");
        OutputStream os = conn.getOutputStream();
        os.write(convertObjectToBytes((image)));
        os.flush();

        conn.connect();
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
