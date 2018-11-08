package com.greer.springbootdemo2.dao;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectRestriction;
import com.greer.springbootdemo2.bean.Mother;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface MotherDao extends JpaRepository<Mother, Integer> {
     Mother getById(Integer id) ;

     @Query(nativeQuery = true,value = "select id , t_username, t_password from t_user where t_username = :username and id > :id")
     List<Mother> findAllByUsernameAndId(@Param("username") String username , @Param("id") Integer id) ;

     @Query(nativeQuery = false)
     List<Mother> findFirstById(Integer id) ;
     List<Mother> findFirstByUsername(String username) ;

     /**
      * 所谓的本地化查询就是，使用sql语句查询，查询的字段名称和表名称是数据库表中的字段定义的名称，否则将会报数据库查询异常
      * 非本地化查询就是使用hibernate自带的查询语句hql，此时查询的字段名和数据库表名称是实体类中定义的属性名称和实体名称。
      * @param username
      * @param password
      * @param id
      * @return
      */
     @Transactional
     @Modifying
     @Query(nativeQuery = true , value = "update #{#entityName} set username = :username,password = :password where id = :id")
     int updateMother(@Param("username") String username , @Param("password") String password , @Param("id") Integer id) ;
     void deleteById(Integer id) ;

     @Transactional
     @Modifying
     @Query(value = "delete from #{#entityName} where id = :id")
     void deleteFromUserById(@Param("id") Integer id) ;

     /**
      * 分页查询
      * @param id
      * @param pageable
      * @return
      */
     @org.springframework.transaction.annotation.Transactional(readOnly = true)
     @Query(nativeQuery = true ,value="select * from t_user where id > :id")
     Page<Mother> findMotherByPage(@Param("id") Integer id , Pageable pageable) ;

     /**
      * 排序查询，将查询的结果集排序
      * @return
      */
     @org.springframework.transaction.annotation.Transactional(readOnly = true)
     @Query(nativeQuery = false ,value = "select id , username , password from #{#entityName}",countQuery = "select count(id) from #{#entityName}")
     List<Mother> findMotherSort(Sort sort) ;

     /**
      * 流式查询
      * @param
      * @return
      */
     @Transactional()
     @Query(nativeQuery = true ,value = "select t_username,t_password from t_user where t_username is not null")
     Stream<Mother> readAllByUsernameNotNull() ;

     /**
      * 异步查询
      * @return
      */
     @Transactional
     @Async
     @Query(nativeQuery = true , value="select * from t_user where t_username is not null")
     Future<List<Mother>> findAllByUsernameIsNotNull() ;

     /**
      * CompletableFuture
      */
     @Transactional
     @Async
     @Query(nativeQuery = true , value="select * from t_user where t_username is not null")
     CompletableFuture<List<Mother>> completableFutureQuery() ;
}
