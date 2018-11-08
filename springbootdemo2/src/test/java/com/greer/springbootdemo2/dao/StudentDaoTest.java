package com.greer.springbootdemo2.dao;

import com.greer.springbootdemo2.SpringUtil.SpringUnit;
import com.greer.springbootdemo2.Springbootdemo2Application;
import com.greer.springbootdemo2.bean.Man;
import com.greer.springbootdemo2.bean.Mother;
import com.greer.springbootdemo2.common.MyBatisUtil;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.swing.*;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = Springbootdemo2Application.class)
public class StudentDaoTest {
    private Logger logger = LoggerFactory.getLogger(StudentDao.class) ;
    @Autowired
    StudentDao studentDao ;
    @Autowired
    MotherDao motherDao ;

    @Ignore
    public void insertTest() {
        logger.info("studentDao : {}" , studentDao);
    }

    @Test
    public void testOneLevelCache () {
        SqlSessionFactory sqlSessionFactory = SpringUnit.getBean(SqlSessionFactory.class) ;
        logger.info("sqlSessionFactory {}" , sqlSessionFactory);
        SqlSession sqlSession = sqlSessionFactory.openSession() ;
        logger.info("sqlSession : {}" , sqlSession);
        studentDao = sqlSession.getMapper(StudentDao.class) ;
//
        int count = studentDao.count();
        logger.debug("count : {}" , count);
        sqlSession.commit();
        //清除缓存 , 当清除缓存时，如果再次执行相同的查询语句，则会直接从sqlSession缓存中去取，而不是再次执行sql语句，
//        sqlSession.clearCache();
//        int result = studentDao.insert(new Man(9999, "zhang" , "12")) ;
//        logger.debug("insert-----> result : {}" , result);
        int countCached = studentDao.count();
        logger.debug("countCached : {}" , countCached);

    }
    @Test
    public void testTwoLevelCache() {
        SqlSessionFactory sqlSessionFactory = SpringUnit.getBean(SqlSessionFactory.class) ;
        //两个不同的session，操作同一条sql语句
        SqlSession sqlSessionOne = sqlSessionFactory.openSession() ;


        StudentDao studentDao  = sqlSessionOne.getMapper(StudentDao.class) ;

        Man man = new Man(11,"", "") ;
        Map<String , Object> conditionMap = new HashMap<String , Object>() ;
        conditionMap.put("id" , 11) ;
        Man newMan = studentDao.selectMan(conditionMap);
//        Man newMan1 = studentDao.selectMan(conditionMap) ;
//
        logger.info("newMan : {}" , newMan);
//        logger.info("newMan1 : {}" , newMan1);
        //二级缓存会在sqlSession提交之后生效，该操作会将缓存添加到Map<>对象中
        sqlSessionOne.commit();



        SqlSession sqlSessionTwo = sqlSessionFactory.openSession() ;
        StudentDao studentDao1 = sqlSessionTwo.getMapper(StudentDao.class) ;
        Man newMan2 = studentDao1.selectMan(conditionMap) ;
        logger.info("newMan1 : {}" , newMan2);
        sqlSessionTwo.commit() ;

//        SqlSession sqlSessionFourth = sqlSessionFactory.openSession() ;
//        StudentDao studentDao3 = sqlSessionFourth.getMapper(StudentDao.class) ;
//        int result = studentDao1.insert(new Man(121,"wang","1212")) ;
//        logger.info("result : {}" , result);
//        sqlSessionTwo.commit() ;

        SqlSession sqlSessionThree = sqlSessionFactory.openSession() ;
        StudentDao studentDao2 = sqlSessionThree.getMapper(StudentDao.class) ;
        Man newMan3 = studentDao2.selectMan(conditionMap) ;
        logger.info("newMan3 : {}" , newMan3);
        sqlSessionTwo.commit() ;

    }


    @Test
    public void testUpdate() {
        SqlSessionFactory sqlSessionFactory = SpringUnit.getBean(SqlSessionFactory.class) ;

        SqlSession studentSqlSession = sqlSessionFactory.openSession() ;
        StudentDao studentDao = studentSqlSession.getMapper(StudentDao.class) ;

        SqlSession smallStudentSqlSession = sqlSessionFactory.openSession();
        SmallStudentDao smallStudentDao = smallStudentSqlSession.getMapper(SmallStudentDao.class) ;

        /**
         * 1 先用SmallStudentDao查一下数据库，似的该mapper namespace产生缓存
         * 2 再用StudentDao更新一SmallStudentDao的所查到的那条数据表记录
         * 3 最后使用SmallStudentDao再次查一下已经查过的记录，观察两次查询的结果是否相同，
         * 如果相同那么就没有产生脏数据，如果不相同，则产生了脏数据。
         * 结果：在studentDao更新了数据记录之后，smallStudentDao再次查询的结果还是原来第一次查询的旧数据，所以要慎用二级缓存
         */
        Map<String , Object> conditioMap = new HashMap<>() ;
        conditioMap.put("id" , 17);

        Man man = smallStudentDao.selectMan(conditioMap) ;
        logger.debug("man : {}" , man);
        smallStudentSqlSession.commit();

        int updateResult = studentDao.updateMan(conditioMap) ;
        logger.debug("updateResult : {}" , updateResult);
        studentSqlSession.commit();

        Man cacheMan = smallStudentDao.selectMan(conditioMap) ;
        logger.debug("cacheMan : {}" , cacheMan);
        smallStudentSqlSession.commit();
    }

    /**
     * 测试jpa
     */
    @Test
    public void testJpa() {
//        List<Mother> motherList = motherDao.findAllByUsernameAndId("sww" , 1) ;
//        logger.info("motherList: {}" , motherList);
        //保存一个对象
        List<Mother> mothers = new ArrayList<>() ;
        Mother mother1 = new Mother() ;
        mother1.setUsername("sun");
        mother1.setPassword("okay");
        mothers.add(mother1) ;

        Mother mother2 = new Mother() ;
        mother2.setUsername("sun");
        mother2.setPassword("okay");
        mothers.add(mother2) ;

        Mother mother3 = new Mother() ;
        mother3.setUsername("sun");
        mother3.setPassword("okay");
        mothers.add(mother3) ;
//        Iterable<Mother> iterable = mothers.i
//        Iterable<Mother> iterable = mothers.iterator() ;
        Iterable<Mother> iterableMother = motherDao.saveAll(mothers) ;
//        logger.info("iterableMother : {}" , iterableMother);

    }

    @Test
    public void findFirstById() {
        List<Mother> firstMother = motherDao.findFirstById(41) ;
        logger.info("firstMother : {}" , firstMother);
        List<Mother> firstMotherByUsername = motherDao.findFirstByUsername("sun") ;
        logger.info("firstMotherByUsername id : {}" , firstMotherByUsername.get(0).getId());
    }
    @Test
    public void update() {
//        List<Mother> firstMother = motherDao.findFirstById(39) ;
//        Mother mother = firstMother.get(0) ;
//        mother.setUsername("二蛋");
//        Mother mother1 = new Mother();
//        mother1.setId(38);
//        mother1.setUsername("五蛋");
        int result = motherDao.updateMother("笑话","1212" , 39) ;
        logger.info("result :{}" , result);
    }

    @Test
    public void deleteById() {
//        motherDao.deleteById(35) ;
//        List<Mother> firstMother = motherDao.findFirstById(35) ;

        //使用自定义的本地删除操作
//        int result = motherDao.deleteFromUserById(39) ;
        /**
         * 本地定义的删除更新操作可以定义方法的返回值,返回值为删除或者更新数据表之后影响的行数，
         * 也可以不定义返回值，即返回值为void，这样也可以删除数据表中的记录，但是作为开发人员
         * 不知道是否删除成功。
         */
//        logger.info("delete result in native : {}" , result);
        motherDao.deleteFromUserById(40);
    }
    @Test
    public void findAllByUsernameAndId() {
        List<Mother> mothers = motherDao.findAllByUsernameAndId("sun" , 41) ;
//        logger.info("mothers : {}" , mothers.get(0));
        logger.info("mothers.get[0] : {}" , mothers.get(0).getUsername());
        logger.info("mothers.get[1] : {}" , mothers.get(1));

    }

    /**
     * 分页查询测试
     */
    @Test
    public void pageFind() {

//        PageRequest pageable = PageRequest.of(2, 2);  //查询第2页，每页大小为2
        Sort sort = new Sort(Sort.Direction.ASC,"id") ;  //按照username升序排序/按照id升序排序
        PageRequest pageable = PageRequest.of(2,2,sort) ;
        PageRequest pageRequest = PageRequest.of(2,2, Sort.Direction.ASC,"username") ;  //按照username升序排序
        Page<Mother> motherPage = motherDao.findMotherByPage(1, pageRequest);
        long allCount = motherPage.getTotalElements();
        logger.info("allCount : {}", allCount);
        int totalPages = motherPage.getTotalPages();
        logger.info("totalPages : {}", totalPages);
        Iterator<Mother> iterator = motherPage.iterator();
        while (iterator.hasNext()) {
            Mother mother = iterator.next();
            logger.info("mother : {}", mother);
        }
    }

    /**
     * 排序查询
     */
    @Test
    public void sortQuery() {
        Sort sort = new Sort(Sort.Direction.ASC , "id") ;
        List<Mother> mothers = motherDao.findMotherSort(sort) ;
        Iterator<Mother> mothersIterator = mothers.iterator() ;
        while(mothersIterator.hasNext()) {
            logger.info("mother : {}" , mothersIterator.next());
        }
//        for (Mother mother : mothers) {
//            logger.info("mother : {}" , mother);
//        }
    }


    /**
     * 流查询
     */
    @Test
    public void streamQuery() {
        try (Stream<Mother> motherStream = motherDao.readAllByUsernameNotNull()) {
            motherStream.forEach(mother -> {
                logger.info("mother : {}", mother);
            });
        }
    }

    /**
     * 异步查询
     */
    @Test
    public void asyncQuery() throws ExecutionException, InterruptedException {
//        Future<List<Mother>> motherFuture = motherDao.findAllByUsernameIsNotNull() ;
//        List<Mother> mother = motherFuture.get();
//        logger.info("mother : {}" , mother);
        CompletableFuture<List<Mother>> listCompletableFuture = motherDao.completableFutureQuery();
        listCompletableFuture.thenAccept(list -> {
            list.forEach(mother -> {
                logger.info("mother : {}" , mother);
            });
        }) ;
    }


}