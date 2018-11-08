package com.greer.springbootdemo2.controller;

import com.greer.springbootdemo2.IService.StudentService;
import com.greer.springbootdemo2.SpringUtil.SpringUnit;
import com.greer.springbootdemo2.bean.Child;
import com.greer.springbootdemo2.bean.Student;
import com.greer.springbootdemo2.bean.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextAttributeFactoryBean;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping("/springbootdemo02")
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(this.toString()) ;
    @Autowired
    private StudentService studentService ;
    @RequestMapping("coming")
    public String index(Model model, ModelMap modelMap ,RedirectAttributes attributes) {
        logger.info("coming!!!!!!!!!!!!!!!!!");
        model.addAttribute("name","zhangsan");
        model.addAttribute("age" , 100023) ;
        attributes.addAttribute("year" ,"120023-2323") ;
        studentService.say();
        return "index";
    }
    @Autowired
    private World world ;

    @Autowired
    private Child child ;


    /**
     * 复习一下：Model,ModelAttribute,RedirectAttributes,ModelMap就是一个对 LinkedMap的继承扩展，并没有太大的意义。
     * Model相当于request对象,用来设置内部转发的属性
     * ModeAttribute用来接收参数
     * RedirectAttributes 用来设置重定向的属性，有点像session,但智能重定向一次，该对象中的属性就会被清除
     * @param name
     * @return
     */
    @RequestMapping("result")
    public String handler(@ModelAttribute(value="year") String name) {
        logger.info("name : {}" , name);
        logger.info("world : {}" , world);
        Student student = (Student)SpringUnit.getBean(Student.class) ;
        logger.info("sdaaastudent : {}" , student);
        return "redirect:anotherIndex" ;
    }

    @RequestMapping("anotherIndex")
    public String handler2(@ModelAttribute(value="year") String year) {
        logger.info("child : {}" , child);
        logger.info("year : {}", year);
        return "index";
    }

    @RequestMapping(value = "welcome",method = RequestMethod.GET)
    public String welcome() {
        int result = 1 / 0;
        return "index" ;
    }

    @RequestMapping(value = "post",method = RequestMethod.POST)
    @ResponseBody
    public Child post(@ModelAttribute Child child) {
        logger.info("post child ----------> {}", child);
        Child localChild = new Child();
        localChild.setName(null);
        localChild.setId(null);
        return localChild ;
    }
    @RequestMapping("hi")
    public String druid () {
        return "druid" ;
    }
}
