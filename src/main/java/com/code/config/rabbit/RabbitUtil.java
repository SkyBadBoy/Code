package com.code.config.rabbit;

import com.code.domain.Access;
import com.code.domain.Error;
import com.code.service.write.AccessService;
import com.code.service.write.ErrorService;
import com.code.until.CommonStatus;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * Created by MaJian on 18/5/15.
 */
public class RabbitUtil {

    private boolean Open=false;

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
}
