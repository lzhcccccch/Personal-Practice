package com.lzhch.thread.exchangedata;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @packageName： com.lzhch.thread.exchangedata
 * @className: ThreadExchangeData
 * @description: 两个线程间交换数据
 * @version: v1.0
 * @author: lzhch
 * @date: 2022/7/14 09:39
 */

public class ThreadExchangeData {

    /**
     * java.util.concurrent 包下提供了两个线程之间交换数据的类
     * V exchange(V v)
     * 线程一直被阻塞 直到其他任意线程和他交换数据的线程或者被中断
     * V exchange(V x, long timeout, TimeUnit unit)
     * 超过超时时间则会报 TimeoutException
     */

    public static void main(String[] args) {
        // exchange(null);
        // exchange(null, 3L);
        // exchangeInterrupted();
        moreThreadExchange();
    }

    /**
     * 只有两个线程正常交换数据
     *
     * @param object
     */
    private static void exchange(Object object) {
        Exchanger<String> exchanger = new Exchanger();

        new Thread(() -> {
            String data = "thread 1001";
            Thread.currentThread().setName(data);
            System.out.println(Thread.currentThread().getName() + ": " + data);

            try {
                String newData = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + "原数据: " + data);
                System.out.println(Thread.currentThread().getName() + "交换后数据: " + newData);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            String data = "thread 2002";
            Thread.currentThread().setName(data);
            System.out.println(Thread.currentThread().getName() + ": " + data);

            try {
                String newData = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + "原数据: " + data);
                System.out.println(Thread.currentThread().getName() + "交换后数据: " + newData);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    /**
     * 如果没有其他线程进行交换数据, 则指定超时时间, 超时后会报错, 线程运行结束 不会继续阻塞
     *
     * @param obj
     * @param timeout
     */
    private static void exchange(Object obj, Long timeout) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            String data = "thread 3003";
            Thread.currentThread().setName(data);
            System.out.println(Thread.currentThread().getName() + ": " + data);
            String newData = null;
            try {
                newData = exchanger.exchange(data, timeout, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + ": " + newData);
        }).start();
    }

    /**
     *  如果没有其他线程进行交换数据会一直阻塞 但是中断线程可以结束线程 不在阻塞
     */
    private static void exchangeInterrupted() {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread thread = new Thread(() -> {
            String data = "thread 4004";
            Thread.currentThread().setName(data);
            System.out.println(Thread.currentThread().getName() + ": " + data);
            String newData = null;
            try {
                newData = exchanger.exchange(data);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ": " + data);
            System.out.println(Thread.currentThread().getName() + ": " + newData);

        });

        thread.start();
        ;

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        thread.interrupt();
    }

    /**
     *  超过两个线程在运行时 也只是会两个线程之间进行交换数据
     */
    private static void moreThreadExchange() {
        Exchanger<String> exchanger = new Exchanger<>();

        for (int i = 0; i < 10; i++) {
            String data = "thread" + i;
            new Thread(()->{
                Thread.currentThread().setName(data);
                String newData = null;
                try {
                    newData = exchanger.exchange(data);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": " + newData);
            }).start();
        }
    }

}
