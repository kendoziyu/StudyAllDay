package j2ee.jdbc.derby;

import java.io.IOException;
import java.sql.*;

import static j2ee.jdbc.derby.Util.getConnection;

/**
 * 描述: 事务的提交与回滚，包含保存点 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/28 0028 <br>
 */
public class TransactionTest {

    public static void main(String[] args) throws IOException {
//        DriverManager.setLogWriter(new PrintWriter(System.out));
        Connection conn = null;
        Savepoint savepoint = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            Statement statement = conn.createStatement();
            statement.executeUpdate("UPDATE Books SET Price='$199.8' WHERE ISBN = '0-201-96426-0'");
            savepoint = conn.setSavepoint();
            // 这段故意写错
            statement.executeUpdate("UPDATE Books SET Price='$20.1' WHERE ISBN = '0-201-96426-0'");
            statement.executeUpdate("UPDATE Books SET Prices='$28.95' WHERE ISBN = '0-201-96426-0'");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    if (savepoint != null) {
                        conn.rollback(savepoint);
                        conn.releaseSavepoint(savepoint);
                        conn.commit(); // 回滚之后，还是需要提交事务的
                    } else {
                        conn.rollback();
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try (Connection conn2 = getConnection()) {
            System.out.println("autoCommit:" + conn2.getAutoCommit());
            Statement statement = conn2.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Price FROM Books WHERE ISBN = '0-201-96426-0'");
            if (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {

        }
    }


}
