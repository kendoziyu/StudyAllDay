package core.java.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

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
        try (InputStream in = Files.newInputStream(path, StandardOpenOption.READ)) {
            System.out.println(in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
