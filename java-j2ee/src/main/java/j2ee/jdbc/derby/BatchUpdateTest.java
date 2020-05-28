package j2ee.jdbc.derby;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static j2ee.jdbc.derby.Util.getConnection;

/**
 * 描述:  关于语句的批量操作 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/29 0029 <br>
 */
public class BatchUpdateTest {

    public static void main(String[] args) throws IOException {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (Statement statement = conn.createStatement()) {
                String dropSQL = "DROP TABLE Greetings";
                statement.addBatch(dropSQL);
                String tableSQL = "CREATE TABLE Greetings(Word CHAR(20))";
                statement.addBatch(tableSQL);
                statement.addBatch("INSERT INTO Greetings(Word) values ('Hello')");
                statement.addBatch("INSERT INTO Greetings(Word) values ('NiHao')");
                int[] results = statement.executeBatch();
                System.out.println(results);
            } catch (SQLException e) {
                e.printStackTrace();
                conn.rollback();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        DriverManager.setLogWriter(new PrintWriter(System.out));
        try (Connection conn = getConnection()) {
            try (Statement statement = conn.createStatement()) {
                try (ResultSet rs = statement.executeQuery("SELECT * FROM Greetings")) {
                    while (rs.next()) {
                        System.out.println(rs.getString(1));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
