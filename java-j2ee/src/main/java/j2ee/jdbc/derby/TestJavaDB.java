package j2ee.jdbc.derby;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

/**
 * 描述: Apache Derby 也称为 Java DB <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/24 0024 <br>
 */
public class TestJavaDB {

    public static void main(String[] args) throws IOException {
        try {
            // 调用DriverManager.setLogWriter方法可以将跟踪信息发送给PrintWriter
            DriverManager.setLogWriter(new PrintWriter("derby.log"));
            runTest();
        } catch (SQLException ex) {
            for (Throwable throwable : ex) {
                throwable.printStackTrace();
            }
        }
    }

    public static void runTest() throws IOException, SQLException {
        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE Greetings (Message CHAR(20))");
            statement.executeUpdate("INSERT INTO Greetings VALUES ('Hello World!')");

            try (ResultSet result = statement.executeQuery("SELECT * FROM Greetings")) {
                if (result.next()) {
                    System.out.println(result.getString(1));
                }
            }

            statement.executeUpdate("DROP TABLE Greetings");
        }
    }

    /**
     * 获取一个 SQL 连接
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public static Connection getConnection() throws IOException, SQLException {
        Properties props = new Properties();

        try (InputStream in = TestJavaDB.class.getClassLoader().getResourceAsStream("database.properties")) {
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null)
            System.out.println(drivers);
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }

}
