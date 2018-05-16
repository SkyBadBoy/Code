package com.code.config.rabbit;

import com.code.domain.Access;
import com.code.domain.Error;
import com.code.service.write.AccessService;
import com.code.service.write.ErrorService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
    @PostConstruct
    public void Init() {
        rabbitHandler = this;
        rabbitHandler.accessService=this.accessService;
    }


    @org.springframework.amqp.rabbit.annotation.RabbitHandler
    @RabbitListener(queues = "WEBLOG")
    public void WebLogHandler(Access access) {
        accessService.insert(access);
    }

    @org.springframework.amqp.rabbit.annotation.RabbitHandler
    @RabbitListener(queues = "ERRORLOG")
    public void ErrorLogHandler(Error error) {
        errorService.insert(error);
    }


}
