package com.code.config.rabbit;

import com.code.until.CommonStatus;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author majian
 *
 * 注入队列
 */
@Configuration
public class RabbitConfig {

    /** 访问日志*/
    @Bean
    public Queue WebLogQueue() {
        return new Queue(CommonStatus.RabbitType.WEBLOG.seq);
    }
    /** 错误日志*/
    @Bean
    public Queue ErrorLogQueue() {
        return new Queue(CommonStatus.RabbitType.ERRORLOG.seq);
    }
    /** 用户操作日志*/
    @Bean
    public Queue OperationLogQueue() {
        return new Queue(CommonStatus.RabbitType.OPERATIONLOG.seq);
    }
    /** 用户登录日志*/
    @Bean
    public Queue UserLoginLogQueue() {
        return new Queue(CommonStatus.RabbitType.USERLOGINLOG.seq);
    }
}
