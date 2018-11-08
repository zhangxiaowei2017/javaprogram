package com.greer.springbootdemo2.SpringUtil;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUnit implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUnit.applicationContext = applicationContext;
    }
    public static <T> T getBean(String beanName) {
        return (T)applicationContext.getBean(beanName) ;
    }
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz) ;
    }
}
