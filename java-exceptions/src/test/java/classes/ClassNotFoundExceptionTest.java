package classes;

import org.junit.Test;


public class ClassNotFoundExceptionTest {

    /**
     * 调用 Class.forName 方法时，找不到指定的类
     */
    @Test
    public void javaReflection() {
        try {
            Class.forName("com.helloworld666.Test");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void systemLoaderLoadClass() {
        try {
            ClassLoader.getSystemClassLoader().loadClass("com.helloworld666.Test");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
