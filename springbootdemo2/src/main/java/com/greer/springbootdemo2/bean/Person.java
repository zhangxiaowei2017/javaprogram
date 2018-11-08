package com.greer.springbootdemo2.bean;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 *  PropertySource注解指定要绑定的属性的文件的位置，如果不指定要绑定的文件的位置，则默认绑定application.properties文件
 *  ConfigurationProperties("person")，开启属性文件的方式注入bean属性，意思就是该注解会把properties文件的属性一一
 *  注入到Person  bean的实体属性中。一般需要提供相应的setter方法,但对某些类型不需要提供setter方法。
 */
@Component
@PropertySource(value = "classpath:application-person.properties")
@ConfigurationProperties(prefix = "person")
//@Scope("prototype")
public class Person {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private int id ;
    private String name ;
    private int age ;
    private Map<String , Object> map ;

    public Person () {
        logger.debug("构造方法属性依赖注入");
    }

    @Autowired
    private Man man ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        logger.info("person 属性依赖注入id ");
        logger.debug("debug level log");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * prefix 会主动到.properties文件中去搜索前缀是:child的属性。
     * @Bean注解其目的就是替代xml文件配置中的<bean></bean>
     * @Configuration表明该类是一个配置类，用于配置spring容器的信息。
     * 注意：如果直接调用createChild方法，则属性不会注入到Child bean中，spring只会将其当成一个普通的方法来使用。
     * @return
     */
    @ConfigurationProperties(prefix = "child")
    @Bean
    public Child createChild() {
        return new Child();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", map=" + map +
                ", man=" + man +
                '}';
    }

    @Component
    @PropertySource(value = "classpath:application-person.properties")
    @ConfigurationProperties(prefix = "man")
    @Validated
    public static class Man{
        @NotNull
        private int id ;
        /**
         * 使用@NotNull注解之后，name不能为null,也就是说再application-person.properties文件中man.name 不能为空。
         */
        @NotNull
        private String name ;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Man{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
