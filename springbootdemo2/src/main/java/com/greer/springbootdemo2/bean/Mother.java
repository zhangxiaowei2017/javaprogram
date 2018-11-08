package com.greer.springbootdemo2.bean;

import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;

@Component
@Entity(name = "user")
@Table(name="t_user")
public class Mother implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;

    @Column(name = "t_username")
    private String username ;
    @Column(name="t_password")
    private String password ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Mother{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
