package com.greer.springbootdemo2;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.greer.springbootdemo2.SpringUtil.SpringUnit;
import com.greer.springbootdemo2.bean.Person;
import com.greer.springbootdemo2.bean.World;
import com.greer.springbootdemo2.dao.StudentDao;
import org.apache.ibatis.javassist.ClassPath;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Profile("development")
@SpringBootApplication
@MapperScan("com.greer.springbootdemo2.dao.StudentDao")
public class Springbootdemo2Application {
    private static Logger logger = LoggerFactory.getLogger(SpringBootApplication.class) ;
//    @Autowired
//    private SqlSessionFactory sqlSessionFactory ;
//    @Bean
//    public SqlSessionFactory getSqlSession() throws IOException {
////        InputStream inputStream = Reader.class.getResourceAsStream("mybatis-config.xml") ;
////        InputStream inputStream = getResourceAsStream("mybatis-config.xml");
//        ClassPathResource resource = new ClassPathResource("mybatis-config.xml") ;
//        File file = resource.getFile();
//        InputStream inputStream = new FileInputStream(file);
//        logger.info("inputStream : {}" , inputStream);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream) ;
//        logger.info("sqlSessionFactory : {}" , sqlSessionFactory);
//        return sqlSessionFactory;
//    }
//    @Bean
//    public SqlSessionTemplate getSqlSessionTemplate() {
//        logger.info("sqlSessionFactory : {}" , sqlSessionFactory);
//        return new SqlSessionTemplate(sqlSessionFactory) ;
//    }
    //在数据库监控配置中如果application.properties和代码中都进行了配置，那么优先代码的配置
    @Bean
    public ServletRegistrationBean statViewServle(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        // IP白名单
        servletRegistrationBean.addInitParameter("allow","192.168.1.218");
        List<String> url = new ArrayList<String>() ;
        url.add("/druid1/*") ;
        url.add("/druid1/druid2/listener/*") ;
        servletRegistrationBean.setUrlMappings(url);
        List<String> druidUrl = new ArrayList<String>() ;
        druidUrl.add("/hello/druid/*") ;
        servletRegistrationBean.setUrlMappings(druidUrl);
        Collection<String> originUrl = servletRegistrationBean.getUrlMappings() ;
        for(String tempUrl : originUrl) {
            System.out.println("tempUrl : " + tempUrl);
        }

        // IP黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny","192.168.1.100");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername","druid");
        servletRegistrationBean.addInitParameter("loginPassword","761341111");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean statFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //忽略过滤的格式
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean
    public World getWorld() {
        return new World();
    }

    @Bean
    public SpringUnit getSpringUnit() {
        return new SpringUnit();
    }

    public static void main(String[] args) {
        SpringApplication.run(Springbootdemo2Application.class, args);
    }
}
