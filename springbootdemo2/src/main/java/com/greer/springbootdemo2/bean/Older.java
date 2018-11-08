package com.greer.springbootdemo2.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * spring.profiles.active配置是将配置文件应用于不同的环境，
 * 比如开发环境，测试环境，生产环境等，以下面的Older例子来讲：
 * 当spring.profiles.active=productionolder,那么在resources
 * 文件夹下的application-productionolder.properties文件将被激活
 * 该文件被激活后，通过@Profile注解，@Profile("productionolder")
 * 那么在该文件中前缀为old的属性将被映射到当前使用@Profile注解的类
 * 的属性上。
 * 可概括为：在application.properties文件中激活profile文件
 * 然后通过@Profile("")注解使用该属性文件。
 */
@Component
@Profile("developementolder")
@ConfigurationProperties(prefix = "old")
public class Older {
    private Logger logger = LoggerFactory.getLogger(this.getClass()) ;

//    @Value("${oldid}")
    private String id ;
//    @Value("${oldname}")
    private String name ;
    public Older() {
        logger.debug("hello,older");
    }

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
        return "Older{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
