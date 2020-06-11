package core.java.settings.preferences;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/6/11 0011 <br>
 */
class PreferenceFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public PreferenceFrame() {
        Preferences root = Preferences.userRoot();
        final Preferences node = root.node("/com/test/corejava");

        // 设置相对位置和宽高
        int left = node.getInt("left", 0);
        int top = node.getInt("top", 0);
        int width = node.getInt("width", DEFAULT_WIDTH);
        int height = node.getInt("height", DEFAULT_HEIGHT);
        setBounds(left, top, width, height);

        // if no title given, ask user
        String title = node.get("title", "");
        if ("".equals(title)) title = JOptionPane.showInputDialog("Please supply a frame title");
        if (title == null) title = "";
        setTitle(title);

        final JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        // 文件过滤
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                // XML 文件和 文件夹
                return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "XML files";
            }
        });

        // 菜单条
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        // 菜单
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        // 导出
        JMenuItem exportItem = new JMenuItem("Export preferences");
        menu.add(exportItem);
        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chooser.showSaveDialog(PreferenceFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try (OutputStream out = new FileOutputStream(chooser.getSelectedFile())) {
                        node.exportSubtree(out);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (BackingStoreException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // 导入
        JMenuItem importItem = new JMenuItem("Import preferences");
        menu.add(importItem);
        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chooser.showOpenDialog(PreferenceFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try (InputStream in = new FileInputStream(chooser.getSelectedFile())) {
                        Preferences.importPreferences(in);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (InvalidPreferencesFormatException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                node.putInt("left", getX());
                node.putInt("top", getY());
                node.putInt("width", getWidth());
                node.putInt("height", getHeight());
                node.put("title", getTitle());
                System.exit(0);
            }
        });
    }
}
