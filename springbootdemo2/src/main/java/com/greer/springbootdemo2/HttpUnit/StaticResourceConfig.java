package com.greer.springbootdemo2.HttpUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 通过代码的形式配置静态资源。
 * 继承WebMvcConfigurer类
 * 然后重写addResourceHandlers方法。
 */
@Component
public class StaticResourceConfig implements WebMvcConfigurer {
    private Logger logger = LoggerFactory.getLogger(this.toString()) ;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        logger.info("addResourceHandlers ");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/anotherstatic/");
    }
}
