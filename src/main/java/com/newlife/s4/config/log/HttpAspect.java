package com.newlife.s4.config.log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;//spring自带的日志框架
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.newlife.s4.common.Sessions;

import javax.servlet.http.HttpServletRequest;

@Aspect//用于将切面声明为一个普通的类
@Component//将这个类引入spring容器中去
public class                                                                                                                                                                                                                                                                                                                     HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);//参数为当前使用的类名

    @Pointcut("execution(public com.alibaba.fastjson.JSONObject com.newlife.s4..*Controller.*(..))")//要处理的方法，包名+类名+方法名
    public void cut(){
    }

    @Before("cut()")//在调用上面 @Pointcut标注的方法前执行以下方法
    public void doBefore(JoinPoint joinPoint){//用于获取类方法
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        logger.info("=====================request begin=======================");
//        try{
//            //logger.info("username ={}",Sessions.getCurrentUserName());
//        }catch (Exception e){
//           // e.printStackTrace();
//        }

        //url
        logger.info("url ={} method={} proxy ip={}",request.getRequestURI(),request.getMethod(),request.getHeader("x-forwarded-for"));
        //method
//        logger.info("method={}",request.getMethod());
//        //ip
//        logger.info("ip={}",request.getRemoteAddr());

//        logger.info("proxy ip={}",request.getHeader("X-Real-IP"));
//        logger.info("proxy  x-forwarded-for:{}",request.getHeader("x-forwarded-for"));
        //类方法
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+'.'+ joinPoint.getSignature().getName());//获取类名及类方法
        //参数
        logger.info("args={}",joinPoint.getArgs());
    }

    @After("cut()")//无论Controller中调用方法以何种方式结束，都会执行
    public void doAfter(){
        //logger.info("----doAfter-----------");
    }

    @AfterReturning(returning = "obj",pointcut = "cut()")//在调用上面 @Pointcut标注的方法后执行。用于获取返回值
    public void doAfterReturning(Object obj){
    	if(obj != null){
    		logger.info("response={}",obj.toString());
    	}
    	logger.info("=====================request end=======================");
    }
}