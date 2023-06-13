package com.lzhch.thread.annotation.async;

import com.lzhch.thread.annotation.async.config.AsyncConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2023/6/12 15:56
 */

@Slf4j
@RestController
@RequestMapping(value = "async")
public class AsyncController {

    @Resource
    private AsyncService asyncService;
    @Resource
    private AsyncConfig asyncConfig;

    @GetMapping(value = "asyncMethod")
    public void asyncMethod() {
        for (int i = 0; i < 10; i++) {
            this.asyncService.asyncMethod();
        }
    }

    @GetMapping(value = "asyncParamMethod")
    public void asyncParamMethod() {
        for (int i = 0; i < 10; i++) {
            CompletableFuture<String> result = null;
            try {
                result = this.asyncService.asyncParamMethod(String.valueOf(i));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            // get、thenApply()、thenAccept() 和 thenRun() 等方法为同步执行, 会造成阻塞, 返回一个新的CompletableFuture对象
            // log.info("controller result :{}", result.get());
            // thenApplyAsync()、thenAcceptAsync() 或 thenRunAsync() 等为异步方法, 会使用线程池去执行, 如果不指定线程池, 则默认采用 ForkJoinPool
            // 在 @Async 异步方法中已经调用 CompletableFuture.completedFuture() 方法, 所以此处只能 ThenXX继续处理
            // 在处理异常时要注意链式调用顺序, 此处应先捕捉@Async方法异常并处理, 再进行后置的处理
            CompletableFuture<String> completableFuture = result.exceptionally(e -> {
                e.printStackTrace();
                // 如果异常需要返回的默认值
                return "处理2的异常";
            }).thenApplyAsync(item -> {
                log.info("controller thread :{}; result :{}", Thread.currentThread().getName(), item);
                return item;
            }, asyncConfig.getAsyncExecutor());

            // CompletableFuture 直接使用异常处理
            // CompletableFuture.supplyAsync(() -> {
            //     log.info("2222222222222异常!!!");
            //     throw new RuntimeException("测试抛出异常!!!");
            // }).exceptionally(e -> {
            //     e.printStackTrace();
            //     // 如果异常需要返回的默认值
            //     return "处理2的异常";
            // });
        }
    }

}
