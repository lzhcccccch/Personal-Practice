package com.lzhch.thread.basic.use.method;

/**
 * @packageName： com.lzhch.thread.basic.use.method
 * @className: ThreadMethodDaemon
 * @description: 守护线程和用户线程
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-02 10:14
 */

import com.lzhch.thread.basic.define.ThreadByInterface;

/**
 *  守护线程和用户线程
 *  守护线程: 精灵线程, 不需要做完. 经典用法: Java垃圾回收机制
 *  用户线程: 前端线程, 必须做完
 */
public class ThreadMethodDaemon {

    public static void main(String[] args) {

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " --- start");

        /**
         *  daemon():
         *      1. 所谓守护线程就是在后台运行的线程, 典型的就是 java 的垃圾机制就是用的这种线程
         *      2. 只要当前 JVM 实例中尚存在任何一个非守护线程没有结束, 守护线程就全部工作;
         *       只有当最后一个非守护线程结束时, 守护线程随着 JVM 一同结束工作;
         *       如果 JVM 中都是守护线程, 当前 JVM 将 exit. 那么守护线程也将随之结束.
         *      3. thread.setDaemon(true) 必须在 thread.start() 之前设置
         *       否则会抛出一个 IllegalThreadStateException 异常, 不能把正在运行的常规线程设置为守护线程
         *      4. 在 Daemon 线程中产生的新线程也是 Daemon 的
         *      5. 主线程结束之后:
         *       5-1. 用户线程将会继续运行
         *       5-1. 如果没有用户线程, 都是守护线程的话, 那么 JVM 将停止运行.
         *        但是 jvm 停止运行并不意味着守护线程立马结束运行, 可能会执行一下但是不一定会完整执行完.
         *      6. 不要在守护线程中写一些会影响业务逻辑以及结果的代码
         */

        ThreadByInterface ti = new ThreadByInterface();
        Thread thread = new Thread(ti);
        thread.setName("threadByInterfaceOut");
        thread.setDaemon(true);
        thread.start();

        System.out.println(threadName + " isDaemon: " + Thread.currentThread().isDaemon());
        System.out.println(threadName + " --- end");

    }

}

/**
 main --- start
 main isDaemon: false
 main --- end
 threadByInterfaceOut --- start.             主线程结束之后, 只有守护线程时, 也不是立马就停止运行, 只是不能保证运行完
 threadByInterfaceOut --- loop at 0
 threadByInterfaceOut --- loop at 1
*/
