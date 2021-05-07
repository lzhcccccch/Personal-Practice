package com.lzhch.thread.basic.use.synchronizedandlock;

import com.lzhch.thread.basic.define.syncobjectlock.SyncMethod;
import com.lzhch.thread.basic.define.syncobjectlock.SyncThreadA;
import com.lzhch.thread.basic.define.syncobjectlock.SyncThreadB;

/**
 * @packageName： com.lzhch.thread.basic.use.synchronizedandlock
 * @className: SyncObjectLock
 * @description: Synchronized 是给对象加锁
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-11-23 10:47
 */
public class SyncObjectLock {

    public static void main(String[] args) {

        /**
         *  synchronized 对象锁
         *      1. synchronized 锁的是对象, 并不是锁一段代码或者一个方法.
         *      2. synchronized 修饰方法时锁定的是调用该方法的对象, 它并不能使调用该方法的多个对象在执行顺序上互斥.
         *      3. 相对于 ReentrantLock 而言, synchronized 锁是重量级锁, 重量级体现在活跃性差一点.
         *          synchronized 锁是内置锁, 意味着 JVM 能基于 synchronized 锁做一些优化: 比如增加锁的粒度(锁粗化)、锁消除.
         *      4.
         */
        SyncMethod methodA = new SyncMethod();
        //SyncMethod methodB = new SyncMethod();

        SyncThreadA threadA = new SyncThreadA(methodA);
        SyncThreadB threadB = new SyncThreadB(methodA);
        //SyncThreadB threadB = new SyncThreadB(methodB);

        threadA.start();
        // 主线程睡眠 1s 再启动 B 线程
        try {
            System.out.println("main sleep");
            Thread.sleep(1000);
            System.out.println("main wake");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadB.start();

    }

}

/**
 *  运行结果分析: 多线程运行结果具有随机性
 *  main sleep
 *  threadAsleep           此处有睡眠
 *  main wake
 *  SyncThreadBsleep       此处未进行睡眠
 *  threadAwake            A wake 之后代码一口气执行完毕, 所以只睡眠一次 5s
 *  threadA--->over! num:100
 *  SyncThreadBwake
 *  SyncThreadB--->over! num:200
 *  由执行结果可知, A 线程启动, main 线程继续运行进入睡眠, main 线程苏醒, B 线程启动进入睡眠, A 苏醒 B 苏醒
 *  由此可见, synchronized 是对对象加锁, 因为并不是 A 执行完 method 又执行 B, 而是 AB 异步进行(在运行时也只睡眠了一次 5s).
 *  这是因为创建了两个 SyncMethod 对象, 并分别赋值给两个线程, 所以相当于是有两把锁, 分别锁住了不同的对象.
 */

/**
 *  注释掉 19 和 23 行代码, 增加 22 行代码分析
 *  main sleep
 *  threadAsleep                 此处 A 睡眠 5s
 *  main wake
 *  threadAwake
 *  threadA--->over! num:100
 *  SyncThreadBsleep             此处 B 睡眠 5s 共两次
 *  SyncThreadBwake
 *  SyncThreadB--->over! num:200
 *  由运行结果可知, A 线程全部执行完再执行 B, 实现了同步, 且在运行时进行了两次 5s 的睡眠, 更加能印证 synchronized 是对象锁
 */
