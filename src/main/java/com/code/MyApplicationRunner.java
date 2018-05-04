package com.code;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 跟随系统一起启动的线程
 *
 * @author majian
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //  自定义线程池  LinkedBlockingQueue  无键队列    new ArrayBlockingQueue<Runnable>(6)			//指定一种队列 （有界队列） 6 代表6个队列  有建队列
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().build();
//        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//        singleThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        //执行完成之后再关闭线程  不过当前是没有运行之后自动回收线程
//        singleThreadPool.shutdown();

    }
}
