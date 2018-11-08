package com.greer.springbootdemo2.bean;

import javax.validation.Valid;

/**
 * 假设该类是一个第三方组件。
 */
public class Child {
    private String id;
    private String name ;
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return "Child{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
