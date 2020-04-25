package jul;

import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 描述:  java.util.logger 的简单应用<br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/4/26 0026 <br>
 */
public class JULTest {

    @Test
    public void example() {
        // 获取一个日志记录对象
        Logger logger = Logger.getLogger("cn.skilled-peon.jul.JULTest");
        // 日志记录输出
        logger.info("hello jul!");

        // 通用方法进行日志记录
        logger.log(Level.INFO, "info msg");

        // 通过占位符方式输出变量值
        String name = "Lisa";
        int age = 18;
        logger.log(Level.INFO, "用户信息，名字：{0}，年龄：{1}", new Object[]{name, age});
    }
}
