package com.greer.springbootdemo2.bean;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Man implements Serializable {
    private int id ;
    private String name ;
    private String age ;
    public Man() {

    }
    public Man(int id ,String name ,  String age ) {
        this.id = id ;
        this.name = name ;
        this.age = age ;
    }


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

    public String getAge() {
        return age;
    }


    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Man{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

}
