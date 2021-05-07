package com.lzhch.thread.basic.define.syncobjectlock;

/**
 * @packageName： com.lzhch.thread.basic.define.syncobjectlock
 * @className: SyncThreadB
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-11-23 11:02
 */
public class SyncThreadB extends Thread {

    private SyncMethod method;

    public SyncThreadB(SyncMethod method) {
        this.method = method;
    }

    @Override
    public void run() {
        method.syncMethod("SyncThreadB", 200);
    }

}
