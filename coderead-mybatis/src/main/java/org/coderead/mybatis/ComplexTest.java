package org.coderead.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.coderead.mybatis.bean.Blog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author tommy
 * @title: ComplexTest
 * @projectName coderead-mybatis
 * @description: 复杂查询
 * @date 2020/6/16:21 PM
 */
public class ComplexTest {

    private SqlSession sqlSession;

    @Before
    public void init() throws SQLException {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = factoryBuilder.build(StatementHandlerTest.class.getResourceAsStream("/mybatis-config.xml"));
        sqlSession = factory.openSession();
    }

    @After
    public void over() {
        sqlSession.close();
    }

    // 1对1 子查询
    @Test
    public void oneToOneTest() {
        Blog selectByBlog = sqlSession.selectOne("selectBlogById", 1);
        assert selectByBlog.getAuthor() != null;
    }

}
