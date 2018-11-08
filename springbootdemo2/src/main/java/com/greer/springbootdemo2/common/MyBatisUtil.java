package com.greer.springbootdemo2.common;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MyBatisUtil {
    @Autowired
    private SqlSessionFactory sqlSessionFactory ;

    public  SqlSession getSqlSession() {
        System.out.println("sqlSessionFactory :" + sqlSessionFactory);
        return sqlSessionFactory.openSession() ;

    }
}
