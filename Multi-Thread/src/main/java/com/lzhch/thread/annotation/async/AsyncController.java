package com.lzhch.thread.annotation.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    @GetMapping(value = "asyncMethod")
    public void asyncMethod() {
        for (int i = 0; i < 10; i++) {
            this.asyncService.asyncMethod();
        }
    }

    @GetMapping(value = "asyncParamMethod")
    public void asyncParamMethod() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            CompletableFuture<String> result = this.asyncService.asyncParamMethod(String.valueOf(i));
            // get 方法会造成阻塞, 它会等待异步操作完成并返回结果, 相当于同步执行
            // log.info("controller result :{}", result.get());
            // thenApply()、thenAccept() 或 thenRun() 为异步方法
            log.info("controller result :{}", result.thenApplyAsync(item-> item));
        }
    }

}
