package com.greer.springbootdemo2.dao;

import com.greer.springbootdemo2.bean.Man;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
public interface StudentDao {
     int count();
     int insert(Man man);
     Man selectMan(Map<String , Object> conditionMap) ;
     int updateMan(Map<String , Object> updateMap) ;
}
