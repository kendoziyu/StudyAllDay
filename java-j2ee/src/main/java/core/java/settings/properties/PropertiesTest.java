package core.java.settings.properties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Properties;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/6/11 0011 <br>
 */
public class PropertiesTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PropertiesFrame frame = new PropertiesFrame();
                frame.setVisible(true);
            }
        });

    }

    static class PropertiesFrame extends JFrame {
        static final int DEFAULT_WIDTH = 300;
        static final int DEFAULT_HEIGHT = 200;

        private Properties settings;
        private File propertiesFile;

        public PropertiesFrame() {
            String userDir = System.getProperty("user.dir");
            File propertiesDir = new File(userDir, ".corejava");
            if (!propertiesDir.exists())
                propertiesDir.mkdir();

            propertiesFile = new File(propertiesDir, "program.properties");

            // 添加键值对
            Properties defaultSettings = new Properties();
            // value 必须使用 String 类型，否则获取不到
            defaultSettings.put("left", "0");
            defaultSettings.put("top", "0");
            defaultSettings.put("width", "" + DEFAULT_WIDTH);
            defaultSettings.put("height", "" + DEFAULT_HEIGHT);
            defaultSettings.put("title", "");

            settings = new Properties(defaultSettings);

            // 如果属性文件存在，加载属性文件
            if (propertiesFile.exists()) {
                try (FileInputStream in = new FileInputStream(propertiesFile)) {
                    settings.load(in);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 设置相对位置和宽高
            int left = Integer.parseInt(settings.getProperty("left"));
            int top = Integer.parseInt(settings.getProperty("top"));
            int width = Integer.parseInt(settings.getProperty("width"));
            int height = Integer.parseInt(settings.getProperty("height"));
            setBounds(left, top, width, height);

            // if no title, ask user
            String title = settings.getProperty("title");
            if ("".equals(title)) {
                title = JOptionPane.showInputDialog("Please supply a frame title:");
                if (title == null) title = "";
                setTitle(title);
            }

            // 监听窗口
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // 当窗口即将关闭时，保存参数到文件中
                    settings.put("left", "" + getX());
                    settings.put("top", "" + getY());
                    settings.put("width", "" + getWidth());
                    settings.put("height", "" + getHeight());
                    settings.put("title", "" + getTitle());

                    try (FileOutputStream out = new FileOutputStream(propertiesFile)) {
                        settings.store(out, "Program Properties");
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                }
            });

        }
    }
}
