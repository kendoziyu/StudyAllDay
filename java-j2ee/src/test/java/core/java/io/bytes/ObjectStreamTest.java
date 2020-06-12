package core.java.io.bytes;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Date;

public class ObjectStreamTest {

    private String testFilePath;

    @Before
    public void init() {
        String folder = System.getProperty("user.dir") + File.separator + ".corejava";
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdir();
        }
        testFilePath = System.getProperty("user.dir") + File.separator + ".corejava" + File.separator + "object1.tmp";
    }

    @Test
    public void writeTest() {
        try {
            FileOutputStream fos = new FileOutputStream(testFilePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.write(12345);
            oos.writeObject("Today");
            oos.writeObject(new Date());
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readTest() {
        try {
            FileInputStream fis = new FileInputStream(testFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            // 顺序不能颠倒
            System.out.println(ois.read());
            System.out.println(ois.readObject());
            System.out.println(ois.readObject());
            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
