package com.lzhch.thread.basic.define.syncobjectlock;

/**
 * @packageName： com.lzhch.thread.basic.define.syncobjectlock
 * @className: SyncThreadA
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-11-23 11:00
 */
public class SyncThreadA extends Thread {

    private SyncMethod method;

    public SyncThreadA(SyncMethod method) {
        this.method = method;
    }

    @Override
    public void run() {
        method.syncMethod("threadA", 100);
    }

}
