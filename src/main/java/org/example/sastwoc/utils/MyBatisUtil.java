package org.example.sastwoc.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // 加载 MyBatis 配置文件
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            // 创建 SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取 SqlSession
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);  // 自动提交事务
    }

    // 获取指定 Mapper 接口的实例
    public static <T> T getMapper(Class<T> mapperClass) {
        SqlSession sqlSession = getSqlSession();  // 获取 SqlSession
        return sqlSession.getMapper(mapperClass); // 获取对应 Mapper
    }
}

