package core.java.io.bytes;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;

public class FileInputStreamTest {

    private String readmeFilePath;
    private String emptyFilePath;

    @Before
    public void init() {
        readmeFilePath = System.getProperty("user.dir") + File.separator + "README.md";
        emptyFilePath  = this.getClass().getClassLoader().getResource("empty").getPath();
    }

    @Test
    public void readTest1() {
        try {
            FileInputStream fin = new FileInputStream(readmeFilePath);
            int b;
            while ((b = fin.read()) != -1) {
                System.out.println(b);
            }
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取一个空文件
     */
    @Test
    public void readTest2() {
        try {
            FileInputStream fin = new FileInputStream(emptyFilePath);
            int b;
            while ((b = fin.read()) != -1) {
                System.out.println(b);
            }
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readTest3() {
        try {
            FileInputStream fin = new FileInputStream(readmeFilePath);
            byte[] buffer = new byte[4];
            while (fin.read(buffer) != -1) {
                System.out.println(Arrays.toString(buffer));
            }
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
