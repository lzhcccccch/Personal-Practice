package com.lzhch.thread.basic.use.method;

/**
 * @packageName： com.lzhch.thread.basic.use.method
 * @className: ThreadMethodPriority
 * @description: 多线程 priority(优先级)
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-01 10:28
 */

import com.lzhch.thread.basic.define.ThreadByInterface;

/**
 *  线程的优先级:
 *  Thread.MAX_PRIORITY   10
 *  Thread.NORM_PRIORITY  5  默认[因此main()方法也是5]
 *  Thread.MIN_PRIORITY   1
 */
public class ThreadMethodPriority {

    public static void main(String[] args) {

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " --- start");
        // 给主线程设置优先级为 8, 启动的线程不设置, 则会默认继承主线程的 8
        //Thread.currentThread().setPriority(8);

        /**
         *  线程优先级: 1~10
         *      1. 线程的优先级范围是 1~10, 超过会报错, 默认为 5.
         *      2. 线程的优先级继承于创建它的线程(在 main 方法中启动线程, 若都不设置优先级, 则默认继承 main 的级别 5)
         *      3. 线程的规则性: 理论上, 优先级高的线程会优先执行完. 但是! 注意! 只是理论上!
         *       JVM线程调度程序是基于优先级的抢先调度机制, 因此优先级高的线程理论上优先执行完, 但是线程有随机性.
         *      4. 线程的随机性: 实际中, 并不一定是优先级高的线程就一定先执行完.
         *       优先级和操作系统及虚拟机版本相关. 优先级设置的高只是代表告知了[线程调度器]该线程的重要程度.
         *       但是当有大量线程都被阻塞等待运行时, 调试程序会首先运行具有最高优先级的那个线程.
         *       但是这并不意味着优先级低的线程不会被运行(换言之, 不会因为存在优先级而导致死锁).
         *       因此优先级低的线程只是表示被允准运行的几率小一些而已, 但是不代表不会被运行.
         *       又或者即使不是大量线程阻塞, 因为线程的执行是无序执行(并不是按照代码写的顺序执行)的,
         *       而优先级低的线程的耗时远小于优先级高的线程, 也可能会导致只要给予优先级低的线程一点运行时间即可完成.
         *       综上, 在实际应用中, 不可认为优先级高的线程就一定会比优先级低的线程先执行完.
         */

        ThreadByInterface ti = new ThreadByInterface();
        Thread thread = new Thread(ti);
        thread.setName("threadByInterface");
        //thread.setPriority(8);
        thread.start();

        for (int i  = 0; i < 5 ; i++) {
            System.out.println(threadName + " --- [main] loop at " + i);
        }
        System.out.println("thread priority: " + thread.getPriority());
        System.out.println(threadName + " --- end!!!");
    }

}
