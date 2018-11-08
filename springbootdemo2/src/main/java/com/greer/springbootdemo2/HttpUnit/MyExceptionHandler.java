package com.greer.springbootdemo2.HttpUnit;

import com.greer.springbootdemo2.controller.IndexController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

/**
 * ControllerAdvice 用在对使用@Controller全局配置：
 * 对controller中的使用@RequestMapping注解的方法添加异常处理。
 * 添加全局属性对在@ModelAttribute注解的方法上。
 * 在执行@RequestMapping方法之前执行数据的初始化绑定,绑定通过@initBinder
 *
 *
 *
 */
@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass().toString()) ;

    /**
     * 处理ArithmeticException异常
     * @param a
     * @param httpStatus
     * @param request
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
//    public String handlerArithmeticException(WebRequest request, Throwable ex) {
    public ResponseEntity handlerArithmeticException(WebRequest request, Throwable ex) {
        logger.info("handler ArithmeticException");
        //获取请求参数name
        String name = request.getParameter("name") ;
        logger.info("name : {}" , name);
        logger.info("webrequest instanceof HttpServeltRequest {}" , request instanceof HttpServletRequest);

        //返回一个ResponseEntity实体。
        return new ResponseEntity(request.getSessionId(),HttpStatus.INTERNAL_SERVER_ERROR) ;
        //返回一个modelview视图。
//        return "ww";
}
}
