package com.code.config.aspect;

import com.code.domain.Access;
import com.code.service.write.AccessService;
import com.code.until.SpringUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Web层日志切面
 *
 * @author majian
 *
 * 优先权是1
 */
@Aspect
@Order(1)
@Component
public class WebLogAspect {

    private Logger logger = Logger.getLogger(getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    private String RequestURL=null;
    private String Method=null;
    private String RemoteAddr=null;
    private String ClassName=null;
    private String Args=null;

    private static WebLogAspect webLogAspect;

    @Autowired
    private AccessService accessService;

    @PostConstruct
    public void Init() {
        webLogAspect = this;
        webLogAspect.accessService = this.accessService;
    }


    @Pointcut("execution(public * com.code.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        RequestURL=request.getRequestURL().toString();
        Method=request.getMethod();
        RemoteAddr=request.getRemoteAddr();
        ClassName=joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Args=Arrays.toString(joinPoint.getArgs());

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        // 记录下请求内容
//        logger.info("Url地址 : " + RequestURL);
//        logger.info("请求方式 : " + Method);
//        logger.info("IP地址: " + RemoteAddr);
//        logger.info("类名 : " + ClassName);
//        logger.info("参数 : " + Args);
//
//        logger.info("返回值 : " + ret);
//        logger.info("处理时间 : " + (System.currentTimeMillis() - startTime.get()));
        /** 目前参数和返回值还没有记录 */
        if(accessService!=null) {
            accessService.insert(Access.getAccess(RequestURL, Method, RemoteAddr, ClassName, Args, (System.currentTimeMillis() - startTime.get())));
        }
    }


}

