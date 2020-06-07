package org.coderead.mybatis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.UUID;

/**
 * @author tommy
 * @title: JDBC
 * @projectName test
 * @description: TODO
 * @date 2020/5/119:28 PM
 */
public class JdbcTest {
    public static final String URL = "jdbc:mysql://47.99.214.246:3899/blog";
    public static final String USERNAME = "luban";
    public static final String PASSWORD = "coderead";
    private Connection connection;

    @Before
    public void init() throws SQLException {
         connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @After
    public void over() throws SQLException {
        connection.close();
    }

    @Test
    public void jdbcTest() throws SQLException {
        // 预编译
        String sql = "SELECT id,name FROM users WHERE `name`=? and sex=? order by id desc";
        PreparedStatement sql1 = connection.prepareStatement(sql);
        sql1.setString(1,"鲁班大叔");
        sql1.setString(2,"女");
        sql1.executeQuery();

        ResultSet resultSet = sql1.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        resultSet.close();
        sql1.close();;
    }

    @Test
    public void prepareBatchTest() throws SQLException {
        String sql = "INSERT INTO `users` (`name`,age) VALUES (?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"鲁班大叔");
        preparedStatement.setString(2,"19");

        preparedStatement.addBatch();;
        preparedStatement.setString(1,"鲁班大叔2");
        preparedStatement.setString(2,"20");
        preparedStatement.addBatch();;

        preparedStatement.executeBatch(); // 批处理  一次发射
        preparedStatement.close();
    }


    // sql注入测试
    public int selectByName(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE `name`='" + name + "'";
        System.out.println(sql);
        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
        ResultSet resultSet = statement.getResultSet();
        int count=0;
        while (resultSet.next()){
            count++;
        }
        statement.close();
        return count;
    }
    // 防止SQL注入


    //PreparedStatement防止 sql注入测试
    public int selectByName2(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE `name`=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,name);
        System.out.println(statement);
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();
        int count=0;
        while (resultSet.next()){
            count++;
        }
        statement.close();
        return count;
    }
    @Test
   public void injectTest() throws SQLException {
        System.out.println(selectByName("鲁班大叔"));
        System.out.println(selectByName("鲁班大叔' or '1'='1"));
        System.out.println(selectByName2("鲁班大叔' or '1'='1"));
    }


    //PreparedStatement防止 sql注入测试
    public void prepareTest() throws SQLException {
        String sql = "SELECT * FROM users WHERE `name`=? and sex=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        // 第一次
        statement.setString(1,"鲁班大叔");
        statement.setString(2,"男");
        statement.executeQuery();
        statement.getResultSet();
        //第二次
        statement.setString(1,"二娃");
        statement.executeQuery();
        ResultSet resultSet = statement.getResultSet();

    }
}
