package com.lzhch.thread.annotation.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2023/6/12 15:33
 */

@Slf4j
@EnableAsync // 在调用异步方法的类上添加该注解, 不是在启动类上
@Service(value = "iAsyncService")
public class AsyncService {

    /**
     * 没有返回值的异步调用
     * <p>
     * Author: lzhch 2023/6/12 18:07
     */
    @Async
    public void asyncMethod() {
        try {
            String name = Thread.currentThread().getName();
            log.info("线程 :{}  开始", name);
            Thread.sleep(3000);
            log.info("线程 :{}  结束", name);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 需要返回值的异步调用
     *
     * @param param param
     * @return CompletableFuture<String>
     * Author: lzhch 2023/6/12 17:03
     */
    @Async
    public CompletableFuture<String> asyncParamMethod(String param) {
        try {
            String name = Thread.currentThread().getName();
            log.info("线程 :{}  开始; 参数 :{}", name, param);
            Thread.sleep(3000);
            log.info("线程 :{}  结束; 参数 :{}", name, param);

            if ("2".equals(param)) {
                log.info("2222222222222异常!!!");
                throw new RuntimeException("测试抛出异常!!!");
            }

            // 直接使用 CompletableFuture 来处理异步的结果, 比Future更加灵活
            return CompletableFuture.completedFuture(param + "执行完毕");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
