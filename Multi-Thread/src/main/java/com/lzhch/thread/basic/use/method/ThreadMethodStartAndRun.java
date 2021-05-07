package com.lzhch.thread.basic.use.method;

/**
 * @packageName： com.lzhch.thread
 * @className: ThreadMethodStartAndRun
 * @description: 多线程 start() 和 run() 方法使用和区别
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-06-24 15:35
 */

import com.lzhch.thread.basic.define.ThreadByClass;
import com.lzhch.thread.basic.define.ThreadByInterface;

/**
 *  1个进程可以包含1个或多个线程, 一个线程就是一个程序内部的一条执行线索
 */
public class ThreadMethodStartAndRun {

    public static void main(String[] args) {

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " --- start");

        /**
         *  Java 多线程:
         *      1. 当一个 CPU 要运行多个线程时, CPU 会给每个线程分配一定的执行时间.
         *      2. start() 方法就是启动线程, 进入就绪状态, 表示可以被 CPU 进行调度,
         *       CPU 会根据时间片原则对每个线程进行调度, 给予每个线程一定的运行时间, 从而实现并发.
         *      3. start() 启动后并不意味着就会被立马执行, 而是等待 CPU 调用, 启动 run() 方法,
         *       所以 run() 方法执行才是真正的执行了线程体(run 方法)里面的代码.
         *      4. run() 方法执行才意味着线程被真正执行, run() 方法中是要执行的代码, 也被称为线程体.
         *      5. 线程启动 start() --> 等待 CPU 调度 --> CPU 调度执行 run() 方法 --> run() 方法执行完毕, 线程死亡
         *      6. 多线程就是分时利用 CPU, 宏观上是让所有线程一起执行, 也叫并发.
         */

        /**
         *  start() 方法:
         *      [启动线程, 真正实现了多线程运行]
         *      1. 调用 start() 方法启动线程, 这时无需等待该线程内的 run() 方法执行完毕, 便可继续执行下面的代码.
         *      2. start() 方法只是启动线程, 并不意味着立马执行该线程, 此时线程只是进入就绪状态,
         *       告诉 CPU 自己可以被调用, 等待 CPU 调度; CPU 调用时线程才进入运行状态, 会执行run() 方法.
         *      3. start() 不能被重复调用, 重复调用会报错.
         *      4. start() 方法是通过调用本地的 start0() 方法, start0() 中会创建一个新的线程,
         *       然后在新的线程中执行 run() 方法. 以如下代码为例:
         *       则表示 start() 启动的线程并不会运行在 main() 方法的主线程上, 而是会运行在一个新的线程上,
         *       与主线程分时利用 CPU, 宏观上让所有线程一起执行, 也叫并发. 所以 start() 方法才是真正实现了并发.
         */

        /**
         *  run() 方法:
         *      [线程体, 直接调用仍然是单线程顺序执行]
         *      1. 调用 run() 方法也会执行相应的线程体中的内容, 但是这种方式就和调用普通的成员方法一样,
         *       会在当前线程中直接调用 run() 方法, 并不会创建新的线程.
         *      2. 直接调用 run() 就是单纯的普通方法的调用, 此时程序仍然是顺序执行, run() 方法执行完毕之后,
         *       才会继续执行下面的代码.
         *      3. run() 方法可以被重复调用, 因为就和普通的成员方法是一样的.
         *      4. 单独调用 run() 方法, 此时程序中还是只有主线程这一个线程, 其程序的执行路径还是只有一条,
         *       会在当前线程中执行 run() 方法, 并不会创建新的线程. 所以单独调用 run() 方法并不会实现多线程.
         */

        ThreadByClass threadClass = new ThreadByClass();
        threadClass.setName("threadByClass    ");
        threadClass.start();
        //threadClass.run();
        try {
            for (int i = 0; i < 5; i++ ) {
                System.out.println(threadName + " --- [main] loop at " + i);
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ThreadByInterface threadInterface = new ThreadByInterface();
        //threadInterface.run();
        Thread thread = new Thread(threadInterface, "threadByInterface");
        /**
         *  使用 Runnable 接口实现多线程时, 调用 start() 方法必须通过 Thread 的构造方法进行传参
         */
        thread.start();

        System.out.println(threadName + " --- end!!!");
    }
    /**
     *  总结:
     *  start() 方法启动线程会启动一个新的线程, 并在新的线程中执行相应 run() 方法, 从而实现并发.
     *  run() 方法就是普通成员方法的调用, 并不会启动新的线程, 会在当前线程中执行 run() 方法, 并不会实现并发.
     */

}
