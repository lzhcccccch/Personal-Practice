package com.lzhch.thread.basic.define;

/**
 * @packageName： com.lzhch.thread.basic.use
 * @className: ThreadByClass
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-06-29 10:22
 */

/**
 *  使用继承父类(Thread)实现多线程, Thread 线程类也是通过实现 Runnable 接口
 */
public class ThreadByClass extends Thread {

    /**
     *  run()方法在线程启动后自动调用
     *  run()方法也被称为线程体, 是一个独立运行的片段
     */
    @Override
    public void run() {

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " --- start");

        /**
         *  start() 和 run() 方法
         */
        try {
            for (int i = 0; i < 5; i++) {
                // 测试 start() 和 run() 方法是否会启动新的线程
                //System.out.println(threadName + " --- [ThreadByClass] loop at " + i);
                System.out.println(threadName + " --- loop at " + i);
                Thread.sleep(1000L);
            }
            //System.out.println(threadName + " --- [ThreadByClass] end.");
            System.out.println(threadName + " --- end.");
        } catch (InterruptedException e) {
            System.out.println("Exception from [" + threadName + "].run");
            e.printStackTrace();
        }

        /**
         *  join() 方法
         */
    }
}
