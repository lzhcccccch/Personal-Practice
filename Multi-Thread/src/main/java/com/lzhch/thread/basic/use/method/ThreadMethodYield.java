package com.lzhch.thread.basic.use.method;

/**
 * @packageName： com.lzhch.thread.basic.use.method
 * @className: ThreadMethodYield
 * @description: 多线程 yield() 方法
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-01 15:21
 */

import com.lzhch.thread.basic.define.ThreadByInterface;

public class ThreadMethodYield {

    public static void main(String[] args) {

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " --- start");

        /**
         *  yield() 方法:
         *      1. 很多翻译成线程让步, 让出CPU, 当前线程进入就绪队列等待调度
         *      2. 使当前线程从执行状态(运行状态)变为可执行状态(就绪状态), 此时 cpu 会从众多的可执行态里面选择
         *       也就是说刚刚的那个线程还是有可能会被再次执行到的, 并不是说一定会执行其他线程而该线程在下一次中不会执行到了.
         *      3. 线程会让出 cpu 执行权, 让自己或其他线程运行.
         *       也就是说, 当前线程调用yield()之后, 并不能保证: 其它具有相同优先级的线程一定能获得执行权,
         *       也有可能是当前线程又进入到 "运行状态" 继续运行.
         *      4. yield() 方法不会释放锁
         */

        ThreadByInterface ti1 = new ThreadByInterface();
        Thread t1 = new Thread(ti1);
        t1.setName("ThreadByInterface111");
        t1.start();

        ThreadByInterface ti2 = new ThreadByInterface();
        Thread t2 = new Thread(ti2);
        t2.setName("ThreadByInterface222");
        t2.start();

        System.out.println(threadName + " --- end");
    }

}
/**
 *  main --- start
 * ThreadByInterface111 --- start.
 * ThreadByInterface111 --- loop at 0
 * ThreadByInterface111 --- loop at 1
 * ThreadByInterface111 --- loop at 2       // 这里线程 1 还没有执行 yield() 方法
 * ThreadByInterface222 --- start.          // 这里是 CPU 自动切换到了线程 2 并不是因为线程 1 执行的 yiel() 方法
 * ThreadByInterface111 --- yield.start     // 这里开始才是线程 1 执行了 yield() 方法
 * ThreadByInterface222 --- loop at 0
 * ThreadByInterface222 --- loop at 1
 * ThreadByInterface222 --- loop at 2
 * ThreadByInterface222 --- yield.start     // 线程 2 执行 yield() 方法
 * ThreadByInterface222 --- yield.end       // 线程 2 执行了 yield() 方法, 又接着继续执行. 此处可以验证上述第 2 点和第 3 点
 *                                              线程会让出 cpu 执行权, 让自己或其他线程运行, 这里是让自己继续运行
 * ThreadByInterface111 --- yield.end       // 下面就是正常的 CPU 的线程调度
 * ThreadByInterface111 --- loop at 3
 * ThreadByInterface111 --- loop at 4
 * ThreadByInterface222 --- loop at 3
 * ThreadByInterface222 --- loop at 4
 * ThreadByInterface222 --- end
 * ThreadByInterface111 --- end
 */

/**
 *  main --- start
 * ThreadByInterface111 --- start.
 * ThreadByInterface222 --- start.
 * ThreadByInterface111 --- loop at 0
 * ThreadByInterface111 --- loop at 1
 * ThreadByInterface222 --- loop at 0
 * ThreadByInterface222 --- loop at 1
 * ThreadByInterface222 --- loop at 2
 * ThreadByInterface111 --- loop at 2
 * ThreadByInterface111 --- yield.start         // 线程 1 执行 yield() 方法, 此时线程会让出 cpu 执行权, 让其他线程运行.
 * ThreadByInterface222 --- yield.start
 * ThreadByInterface111 --- yield.end
 * ThreadByInterface222 --- yield.end
 * ThreadByInterface111 --- loop at 3
 * ThreadByInterface222 --- loop at 3
 * ThreadByInterface222 --- loop at 4
 * ThreadByInterface111 --- loop at 4
 * ThreadByInterface111 --- end
 * ThreadByInterface222 --- end
 */
