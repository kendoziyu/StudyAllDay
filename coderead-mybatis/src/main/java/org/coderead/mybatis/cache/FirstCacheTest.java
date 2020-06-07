package org.coderead.mybatis.cache;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.coderead.mybatis.ExecutorTest;
import org.coderead.mybatis.UserMapper;
import org.coderead.mybatis.bean.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;
import java.util.List;

/**
 * @author tommy
 * @title: FirstCache
 * @projectName coderead-mybatis
 * @description: 一级缓存测试
 * @date 2020/5/318:54 AM
 */
public class FirstCacheTest {
    private SqlSessionFactory factory;
    private SqlSession sqlSession;

    @Before
    public void init() throws SQLException {
        // 获取构建器
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        // 解析XML 并构造会话工厂
        factory = factoryBuilder.build(ExecutorTest.class.getResourceAsStream("/mybatis-config.xml"));
        sqlSession = factory.openSession();
    }

    // 1.sql 和参数必须相同
    // 2.必须是相同的statementID
    // 3.sqlSession必须一样 （会话级缓存）
    // 4.RowBounds 返回行范围必须相同
    @Test
    public void test1(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // org.coderead.mybatis.UserMapper.selectByid
        User user = mapper.selectByid(10);
        RowBounds rowbound=RowBounds.DEFAULT;
        List user1 = sqlSession.selectList(
                "org.coderead.mybatis.UserMapper.selectByid", 10,rowbound);
        System.out.println(user == user1.get(0));
    }

    // 1.未手清空
    // 2.未调用 flushCache=true的查询
    // 3.未执行Update
    // 4.缓存作用域不是 STATEMENT-->
    @Test
    public void test2(){
        // 会话生命周期是短暂的
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByid3(10);
        sqlSession.commit();// clearCache()
        sqlSession.rollback();// clearCache()
//        mapper.setName(11,"道友永存");// clearCache()
        User user1 = mapper.selectByid3(10);// 数据一至性问题
        System.out.println(user == user1);
    }

    @Test
    public void test3(){
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user = mapper.selectByid(10);
        User user1 =mapper.selectByid(10);
        System.out.println(user == user1);
    }

    @Test
    public void testBySpring(){
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring.xml");
        UserMapper mapper = context.getBean(UserMapper.class);
     //   动                             动                           MyBatis
    // mapper ->SqlSessionTemplate --> SqlSessionInterceptor-->SqlSessionFactory

        DataSourceTransactionManager transactionManager =
                (DataSourceTransactionManager) context.getBean("txManager");
        // 手动开启事物
        TransactionStatus status = transactionManager
                .getTransaction(new DefaultTransactionDefinition());

        User user = mapper.selectByid(10); // 每次都会构造一个新会话 发起调用

        User user1 =mapper.selectByid(10);// 每次都会构造一个新会话 发起调用

        System.out.println(user == user1);


    }

}
