package core.java.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/6/11 0011 <br>
 */
public class FilesTest {

    @Test
    public void inputStream() {
        Path path = Paths.get(System.getProperty("user.dir"), "README.md");
        try (InputStream in = Files.newInputStream(path, new OpenOption[]{})) {
            System.out.println(in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
