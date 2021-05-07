package com.lzhch.thread.basic.use.method;

import com.lzhch.thread.basic.define.ThreadByInterface;

/**
 * @packageName： com.lzhch.thread.basic.use.producerandconsumer
 * @className: ObjectMethodWait
 * @description: Java 中的 wait() 方法
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-07 15:16
 */
public class ObjectMethodWait {

    public static void main(String[] args) {

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " --- start");

        /**
         *  wait() 方法:
         *      1. wait() 方法是 Object 类中的, 并不是 Thread 中的!
         *      2. 执行 wait() 方法, JVM 会将当前线程放置于预执行队列(等待池), 代码会在此处停止不再继续执行,
         *       直到另一个线程调用当前对象的 notify() 方法或者 notifyAll() 方法进行唤醒.
         *      3. wait() 方法只能在同步方法或者同步代码块(synchronized)中进行使用,
         *       如果调用 wait() 方法而没有持有适当的锁, 那么就会出现异常.
         *       因为只有共享资源的读写需要同步化(加锁), 如果不是共享资源, 那么就不需要同步化操作.
         *      4. wait() 方法会释放锁, sleep() 方法不会释放锁.
         *      5. 使用了 wait() 方法, 一定要是使用 notify() 或者 notifyAll() 方法进行唤醒,
         *       否则线程会一直在等待池中, 并不会结束, 程序也不会结束.
         *      6. 当前线程调用共享对象的 wait() 方法时, 当前线程只会释放当前共享对象的锁,
         *       当前线程持有的其他共享对象的监视器锁并不会被释放
         *      7. 当一个线程调用共享对象的 wait() 方法被挂起后, 如果其他线程中断了该线程,
         *       则该线程会抛出 InterruptedException 异常并返回
         */

        ThreadByInterface ti = new ThreadByInterface();
        Thread t = new Thread(ti);
        t.setName("threadByInterface");
        t.start();

        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(threadName + " --- loop at " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(threadName + " --- end");

    }

}

/**
 * main --- start                       wait() 方法需在同步代码块中执行
 *  main --- loop at 0                  由执行结果可知:
 *  threadByInterface --- start.         如果线程进入等待并且没有进行唤醒 那么就会一直在等待
 *  main --- loop at 1                   此时, 程序并不会结果执行
 *  main --- loop at 2
 *  main --- loop at 3
 *  main --- loop at 4
 *  main --- end
*/