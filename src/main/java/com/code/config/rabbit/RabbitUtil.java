package com.code.config.rabbit;

import com.code.domain.Access;
import com.code.domain.Error;
import com.code.domain.Online;
import com.code.service.read.*;
import com.code.service.write.*;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MaJian on 18/5/15.
 */
public class RabbitUtil {

    private boolean Open=true;

    private RabbitUtil(){}

    private static RabbitUtil instance = new RabbitUtil();

    public static RabbitUtil getInstance(){
        return instance;
    }

    public void WebLog(Access access, AccessService accessService,AmqpTemplate rabbitTemplate){
        if(Open){
            rabbitTemplate.convertAndSend(CommonStatus.RabbitType.WEBLOG.seq,access);
        }else{
            accessService.insert(access);
        }
    }

    public void ErrorLog(Error error, ErrorService errorService, AmqpTemplate rabbitTemplate){
        if(Open){
            rabbitTemplate.convertAndSend(CommonStatus.RabbitType.ERRORLOG.seq,error);
        }else{
            errorService.insert(error);
        }
    }

    public void UserLoginLog(String sessionID,String userID,int onLineType,ReadOnlineService readOnlineService, OnlineService onlineService, AmqpTemplate rabbitTemplate){
        if(Open){
            Map query=new HashMap();
            query.put("sessionID",sessionID);
            query.put("userID",userID);
            query.put("onLineType",onLineType);
            rabbitTemplate.convertAndSend(CommonStatus.RabbitType.USERLOGINLOG.seq,query);
        }else{
            Online Online= CommonUntil.getInstance().CreateOnline(sessionID,userID,onLineType,readOnlineService,onlineService);

        }
    }
    public void OperationLog(String token,String message,ReadOnlineService readOnlineService, OperationService operationService, AmqpTemplate rabbitTemplate,ReadUserService readUserService){
        if(Open){
            Map query=new HashMap();
            query.put("token",token);
            query.put("message",message);
            rabbitTemplate.convertAndSend(CommonStatus.RabbitType.OPERATIONLOG.seq,query);
        }else{
            CommonUntil.getInstance().CreateOperation(token,message,readOnlineService,operationService,readUserService);
        }
    }
}
