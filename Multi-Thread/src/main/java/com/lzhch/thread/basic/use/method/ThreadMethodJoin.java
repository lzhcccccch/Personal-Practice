package com.lzhch.thread.basic.use.method;

/**
 * @packageName： com.lzhch.thread.basic.use.method
 * @className: ThreadMethodJoin
 * @description: 多线程 join() 方法使用
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-06-29 15:57
 */

import com.lzhch.thread.basic.define.ThreadByInterface;

/**
 *  join让线程串口化
 *  一个线程(ThreaA)必须等待另一个线程(ThreaB)的加入, 并且 ThreadB 执行完毕后 ThreadA 才能继续执行
 */
public class ThreadMethodJoin {

    public static void main(String[] args) {

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " --- start");

        /**
         *  join() 方法:
         *      [让线程串口化: 调用某线程B的该方法,将当前线程A与该线程B"合并",即等待该线程B结束,再恢复当前线程A的运行]
         *      1. 启动线程(start)之后直接调用 join() 方法, 该线程便会立马执行.
         *      2. 使用场景:
         *       很多情况下, 主线程生成并启动了子线程, 如果子线程里要进行大量的耗时运算, 那么主线程极大可能会先于子线程结束;
         *       但是如果主线程处理完其他事务后需要用到子线程的执行结果, 也就是主线程需要等待子线程执行完成之后再结束,
         *       这个时候就要用到 join() 方法了.
         *      3. join() 方法用来暂停当前线程知道 join 操作上的线程执行结束, java 中有 3 个重载的 join() 方法:
         *       3-1. public final void join():
         *        此方法会把当前线程变为wait, 直到执行 join 操作的线程结束;
         *        如果该线程在执行中被中断, 则会抛出 InterruptedException.
         *       3-2. public final synchronized void join(long millis):
         *        此方法会把当前线程变为 wait, 直到执行 join 操作的线程结束或者在执行 join 后等待 millis 的时间.
         *        因为线程调度依赖于操作系统的实现, 因为这并不能保证当前线程一定会在 millis 时间变为 RUnnable.
         *       3-3. public final synchronized void join(long millis, int nanos):
         *        此方法会把当前线程变为 wait, 直到执行join操作的线程结束或者在 join 后等待 millis+nanos 的时间.
         *      4. join() 方法不会释放锁
         */

        ThreadByInterface ti = new ThreadByInterface();
        Thread thread = new Thread(ti);
        thread.setName("threadByInterface");
        thread.start();
        try {
            Thread.sleep(1000);
            //thread.join();
        } catch (InterruptedException e) {
            System.out.println("Exception from [" + threadName + "].run");
            e.printStackTrace();
        }
        System.out.println(threadName + " --- end!!!");
    }

}

/**
 *  运行结果分析:
 *  main --- start                          主线程启动, 因为 sleep(1000) 主线程并没有立马结束
 * threadByInterface --- start              threadByInterface 线程启动
 * threadByInterface --- loop at 0
 * threadByClass     --- start              threadByClass 线程启动
 * threadByClass     --- loop at 0
 * threadByInterface --- loop at 1
 * threadByClass     --- loop at 1
 * threadByInterface --- loop at 2          threadByInterface 中 i=2 时, 调用 threadByClass.join()
 * threadByClass     --- loop at 2
 * threadByClass     --- loop at 3
 * threadByClass     --- loop at 4
 * threadByClass     --- end.
 * threadByInterface --- loop at 3          线程 threadByInterface 在 threadByClass.join() 阻塞处起动, 向下继续执行
 * threadByInterface --- loop at 4
 * threadByInterface --- end.
 * main --- end!!!                      线程 threadByInterface 结束, 此线程在 threadByInterface.join() 阻塞处起动
 */

/**
 *  修改一下代码(注释掉 51 行[thread.join();]), 运行结果分析
 *  main --- start
 * threadByInterface --- start
 * threadByInterface --- loop at 0
 * threadByClass     --- start
 * threadByClass     --- loop at 0
 * threadByInterface --- loop at 1
 * main --- end!!!                      在 sleep 一秒后主线程结束, threadByInterface 执行的 threadByClass.join() 并不会影响到主线程。
 * threadByClass     --- loop at 1
 * threadByInterface --- loop at 2
 * threadByClass     --- loop at 2
 * threadByClass     --- loop at 3
 * threadByClass     --- loop at 4
 * threadByClass     --- end.
 * threadByInterface --- loop at 3
 * threadByInterface --- loop at 4
 * threadByInterface --- end.
 *
 * 由上可以看出, 调用 join() 方法是在当前线程上进行, 因为 threadByClass.join() 的执行是在 threadByInterface 中进行
 * 所以 threadByClass.join() 不会对主线程 main 造成任何影响, 只会影响到当前线程 threadByInterface
 */
