package org.coderead.mybatis;

import org.apache.ibatis.session.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tommy
 * @title: Context
 * @projectName coderead-mybatis
 * @description: TODO
 * @date 2020/5/293:08 PM
 */
public class Context {
    private SqlSession sqlSession;
    private UserMapper mapper;

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


    @Test
    public void test(){
        List<Object> list=new ArrayList<>();
        ResultHandler handler=new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
                if (resultContext.getResultCount()==1) {
                    resultContext.stop();
                }
                list.add(resultContext.getResultObject());
            }
        };
        sqlSession.select("org.coderead.mybatis.UserMapper.selectByid4",handler);
        System.out.println(list.size());

    }

}
