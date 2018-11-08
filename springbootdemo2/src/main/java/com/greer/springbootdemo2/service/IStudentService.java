package com.greer.springbootdemo2.service;

import com.greer.springbootdemo2.IService.StudentService;
import com.greer.springbootdemo2.bean.*;
import com.greer.springbootdemo2.dao.StudentDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IStudentService implements StudentService {
    private Logger logger = LoggerFactory.getLogger(this.toString()) ;

//    @Resource(name="istudentdao")
    /*@Resource
    */
    @Resource
    private StudentDao studentDao ;
    @Autowired
    private SqlSession sqlSession ;
    @Autowired
    private Student student ;
    @Autowired
    private Person person ;
    @Autowired
    private Child child;
    @Autowired
    private Older older ;


//    public IStudentService(Student student,@Resource(name="") StudentDao studentDao) {
//        this.student = student;
//        this.istudentdaoe = studentDao;
//        logger.info("student : {}", this.student);
//    }

    @Override
    public void say() {
        logger.info("i am IStudentService");
        int result = studentDao.count();
//        StudentDao studentDao = sqlSession.getMapper(StudentDao.class) ;
//        int result = studentDao.count();
        logger.info("count result is : {}" , result);
        Man man = new Man();
        man.setName("张华华");
        man.setAge("354");
        result = studentDao.insert(man);
        logger.info("insert result is : {}" , result);
        World world = student.getWorld();
        logger.info("IStudentService ---> world : {}" , world);

//        istudentdaoe.say();
        logger.info("student : {}" , student);
        logger.info("person : {}", person);
        logger.info("person.map.man.name : {}" , person.getMap().get("man"));
        logger.info("parent.createChild : {}" ,child);
        logger.info("older : {}" , older);
    }
}
