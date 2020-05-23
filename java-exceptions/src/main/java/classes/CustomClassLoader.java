package classes;

import java.io.*;

public class CustomClassLoader extends ClassLoader {

    /**
     * 指定类加载器去D:\test\文件夹去寻找.class文件，并且打开文件流，并把字节数据写入字节流中
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = null;
        //将点替换成斜杠
        String fileName = name.replaceAll("\\.", "/");
        StringBuilder sb = new StringBuilder("D:");
        sb.append(File.separator);
        sb.append("test");
        sb.append(File.separator);
        sb.append(fileName);
        sb.append(".class");
        fileName = sb.toString();
        try (InputStream is = new FileInputStream(fileName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int r = 0;
            while ((r = is.read(buf)) != -1) {
                bos.write(buf, 0, r);
            }
            bytes = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}
