package com.lzhch.thread.basic.define.syncobjectlock;

/**
 * @packageName： com.lzhch.thread.basic.define.syncobjectlock
 * @className: SyncMethod
 * @description: 同步方法类
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-11-23 10:53
 */
public class SyncMethod {

    private int num = 0;

    public synchronized void syncMethod(String threadName, int num) {
        try {
            this.num = num;
            // 睡眠 5s 防止执行过快 无法验证加锁效果
            System.out.println(threadName + "sleep");
            Thread.sleep(5000);
            System.out.println(threadName + "wake");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadName + "--->over! num:" + num);
    }
}
