package com.code.config.rabbit;

import com.code.domain.Access;
import com.code.domain.Error;
import com.code.domain.Online;
import com.code.domain.Operation;
import com.code.service.write.*;
import com.code.service.read.*;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MaJian on 18/5/15.
 */
@lombok.extern.log4j.Log4j
@Component
public class RabbitHandler {

    private static RabbitHandler rabbitHandler;

    @Autowired
    private AccessService accessService;
    @Autowired
    private ErrorService errorService;
    @Autowired
    private OnlineService onlineService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private ReadUserService readUserService;
    @Autowired
    private ReadOnlineService readOnlineService;
    @PostConstruct
    public void Init() {//保险点就是这么写,这块不写也没事
        rabbitHandler = this;
        rabbitHandler.accessService=this.accessService;
        rabbitHandler.onlineService=this.onlineService;
        rabbitHandler.errorService=this.errorService;

        rabbitHandler.readOnlineService=this.readOnlineService;
    }

    /**接口访问日志 */
    @org.springframework.amqp.rabbit.annotation.RabbitHandler
    @RabbitListener(queues = "WEBLOG")
    public void WebLogHandler(Access access) {
        try{
            access.setArgs(access.getArgs().length()>800?access.getArgs().substring(0,800):access.getArgs());
            accessService.insert(access);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**错误日志 */
    @org.springframework.amqp.rabbit.annotation.RabbitHandler
    @RabbitListener(queues = "ERRORLOG")
    public void ErrorLogHandler(Error error) {
        try{
            errorService.insert(error);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**用户操作日志 */
    @org.springframework.amqp.rabbit.annotation.RabbitHandler
    @RabbitListener(queues = "OPERATIONLOG")
    public void OPERATIONLOG(Map query) {
        try{
            String token=query.get("token").toString();
            String message=query.get("message").toString();
            CommonUntil.getInstance().CreateOperation(token,message,readOnlineService,operationService,readUserService);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**用户登录日志 */
    @org.springframework.amqp.rabbit.annotation.RabbitHandler
    @RabbitListener(queues = "USERLOGINLOG")
    public void UserLoginLogHandler(Map query) {
        try{
            String sessionID=query.get("sessionID").toString();
            String userID=query.get("userID").toString();
            int onLineType=Integer.parseInt(query.get("onLineType").toString());
            Online Online= CommonUntil.getInstance().CreateOnline(sessionID,userID,onLineType,readOnlineService,onlineService);
        }catch(Exception e){
            e.printStackTrace();
        }
    }




}
