package com.greer.springbootdemo2.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Student implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(this.toString()) ;

    @Resource
    private  World world ;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }



    public Student() {
        System.out.println("world : " + world);
    }

    /**
     * 学生id,该值是从application.properties文件中key为id的值得到。
     */
    @Value("${id}")
    private int id ;

    /**
     * 学生name,该值是从application.properties文件中key为name的值得到。
     */
    @Value("${name}")
    private String name ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }



    /**
     * 该方法会在SpringApplication.run方法完成之前执行一次。
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        logger.info("student is run()");
        logger.info("student.name : {}" , this.name);
        logger.info("student.id : {}" , this.id);
    }
}
