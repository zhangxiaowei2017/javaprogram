package com.greer.springbootdemo2.dao;

import com.greer.springbootdemo2.bean.Man;

import java.util.Map;

public interface SmallStudentDao {
    Man selectMan(Map<String , Object> conditionMap) ;
}
