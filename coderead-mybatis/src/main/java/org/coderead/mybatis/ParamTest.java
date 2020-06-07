package org.coderead.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author tommy
 * @title: ParamTest
 * @projectName coderead-mybatis
 * @description:
 * @date 2020/6/710:47 AM
 */
public class ParamTest {


    private SqlSession sqlSession;
    private UserMapper mapper;

    @Before
    public void init() throws SQLException {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = factoryBuilder.build(StatementHandlerTest.class.getResourceAsStream("/mybatis-config.xml"));
        sqlSession = factory.openSession();
        mapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void over() {
        sqlSession.close();
    }

    // 单个参数
    @Test
    public void singleTest() {
        mapper.selectByid(10);
    }
    @Test
    public void test2() {
        mapper.selectByNameOrAge("鲁班", Mock.newUser());
    }


}
