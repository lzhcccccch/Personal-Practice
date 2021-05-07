package com.lzhch.thread.basic.define;

/**
 * @packageName： com.lzhch.thread.basic.use
 * @className: ThreadByInterface
 * @description: 使用线程接口(Runnable)实现多线程
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-06-29 10:23
 */

/**
 *  使用线程接口(Runnable)实现多线程, 接口中只定义了 run() 方法
 *  Runnable 接口使用 @FunctionalInterface 注解修饰, @FunctionalInterface 函数式接口
 */

/**
 *  使用 Runnable 接口实现多线程的好处:
 *      1)使用Runnable接口可以避免由于Java的单继承性带来的局限
 * 		2)适合多个相同的程序代码的线程去处理同一资源情况,把线程同程序的代码/数据有效的分离
 */
public class ThreadByInterface implements Runnable {
    @Override
    public void run() {

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " --- start.");

        /**
         *  start() 和 run() 方法
         */
    /*
        try {
            for (int i = 0; i < 5; i++) {
                // 测试 start() 和 run() 方法是否会启动新的线程
                //System.out.println(threadName + " --- [ThreadByInterface] loop at " + i);
                System.out.println(threadName + " --- loop at " + i);
                Thread.sleep(1000L);
            }
            //System.out.println(threadName + " --- [ThreadByInterface] end.");
        } catch (InterruptedException e) {
            System.out.println("Exception from [" + threadName + "].run");
            e.printStackTrace();
        }
     */

        /**
         *  join() 方法
         */
    /*
        try {
            Thread thread = new Thread(new ThreadByClass());
            thread.setName("threadByClass    ");
            thread.start(); // 切记一定要先 start 再 join, 否则 join 不生效
            for (int i = 0; i < 5; i++) {
                System.out.println(threadName + " --- loop at " + i);
                Thread.sleep(1000);
                if (i==2) {
                    thread.join();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Exception from [" + threadName + "].run");
            e.printStackTrace();
        }
    */

        /**
         *  yield() 方法
         */
    /*
        for (int i = 0; i < 5; i++) {
            System.out.println(threadName + " --- loop at " + i);
            if (i==2) {
                System.out.println(threadName + " --- yield.start");
                Thread.yield();
                System.out.println(threadName + " --- yield.end");
            }
        }
    */

        /**
         *  daemon
         */
    /*
        Thread thread = new Thread(new ThreadByInterface());
        thread.setName("threadByInterfaceIn");
        thread.start();
        for (int i = 0; i < 5; i++) {
            System.out.println(threadName + " --- loop at " + i);
        }
        System.out.println(threadName + " isDaemon: " + thread.isDaemon());
    */

        /**
         *  Object  wait()
         */
        try {
            synchronized (this) {
                this.wait();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(threadName + " --- loop at " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Exception from [" + threadName + "].run");
            e.printStackTrace();
        }

        System.out.println(threadName + " --- end");
    }
}
/**
 *  函数式接口: 这种类型的接口也称为SAM接口，即Single Abstract Method interfaces
 *  特点:
 *      1. 接口中有且只有一个抽象方法
 *      2. 允许定义静态方法
 *      3. 允许定义默认方法
 *      4. 允许 java.lang.Object 中的 public 方法
 *      5. 该注解不是必须的, 如果一个接口符合"函数式接口"定义, 那么加不加该注解都没有影响.
 *         加上该注解能够更好地让编译器进行检查.
 *         如果编写的不是函数式接口, 但是加上了 @FunctionInterface, 那么编译器会报错
 */
