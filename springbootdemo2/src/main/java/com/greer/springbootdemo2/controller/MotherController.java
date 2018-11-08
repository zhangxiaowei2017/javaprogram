package com.greer.springbootdemo2.controller;

import com.greer.springbootdemo2.bean.Mother;
import com.greer.springbootdemo2.service.MotherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mother")
public class MotherController {
    private Logger logger = LoggerFactory.getLogger(this.getClass()) ;
    @Autowired
    private MotherService motherService ;

    @RequestMapping(value = "/{id}" ,produces = "application/json;charset=utf-8")
    public Mother getMother(@PathVariable("id") Integer id , Model model) {
        Mother mother = motherService.getMother(id) ;
        logger.info("mother : {}" , mother);
        logger.info("mother.getName : {}" , mother.getPassword());
        return  mother;
    }
}
