package catharsis.image_server;

import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class Controller {

    final static private String ROOT_PATH = Paths.get(System.getProperty("user.dir"), "image_root").toString();

    Controller() throws Exception {
        /* 이미지 root 디렉토리가 없으면 생성 */
        final File file = new File(ROOT_PATH);
        file.mkdir();
        /*********************************/
    }

    // /path/filename 에 있는 파일 전송
    @GetMapping("/{path}/{filename}")
    public byte[] getImage(@PathVariable(value="path") String path, @PathVariable(value="filename") String filename) throws Exception {
        final String full_path = Paths.get(ROOT_PATH, path, filename).toString();
        return Files.readAllBytes(new File(full_path).toPath());
    }

    // /path/ 에 있는 파일 갯수 반환
    @GetMapping("/{path}/image-count")
    public int getImageCount(@PathVariable(value="path") String path) {
        final File dir = new File(Paths.get(ROOT_PATH, path).toString());
        if (dir.list() == null) {
            return 0;
        }

        return dir.list().length;
    }

    // /path/filename 에 이미지 저장
    @PostMapping("/{path}/{filename}")
    public void setImage(@PathVariable(value="path") String path, @PathVariable(value="filename") String filename, @RequestBody byte[] image) throws Exception {
        /* 디렉토리 없으면 생성 */
        final File file = new File(Paths.get(ROOT_PATH, path).toString());
        file.mkdir();
        /*******************/

        /* 파일 저장 */
        final String full_path = Paths.get(ROOT_PATH, path, filename).toString();
        File lOutFile = new File(full_path);
        FileOutputStream lFileOutputStream = new FileOutputStream(lOutFile);
        lFileOutputStream.write(image);
        lFileOutputStream.close();
        /***********/
    }
}
