package jul;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

/**
 * 描述:  java.util.logger 的简单应用<br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/4/26 0026 <br>
 */
public class JULTest {

    @Test
    public void testQuickStart() {
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

    @Test
    public void testLogConfig() throws IOException {

        Logger logger = Logger.getLogger("cn.skilled-peon.jul.JULTest");

        // 关闭系统默认配置
        logger.setUseParentHandlers(false);

        Formatter formatter = new SimpleFormatter();

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler);

        FileHandler fileHandler = new FileHandler("/logs/jul.log");
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);

        // 配置日志级别
        logger.setLevel(Level.ALL);

        logger.info("testLogConfig");
    }


    /**
     * java.util.logging.logger 存在父子关系
     * 如果父包包含子包的话，子包会默认继承父包
     *
     */
    @Test
    public void testParent() {
        Logger logger1 = Logger.getLogger("com.alibaba");
        Logger logger2 = Logger.getLogger("com");

        Assert.assertTrue(logger1.getParent() == logger2);
        System.out.println("root parent:" + logger2.getParent() + ", name:" + logger2.getParent().getName());

    }

    @Test
    public void testLogProperties() throws IOException {
        // 读取类加载文件，通过类加载器
        InputStream ins = JULTest.class.getClassLoader().getResourceAsStream("logging.properties");
        // 创建 LogManager
        LogManager logManager = LogManager.getLogManager();
        // 通过 LogManager 加载配置文件
        logManager.readConfiguration(ins);

        Logger logger = Logger.getLogger("cn.skilled-peon");
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }
}
