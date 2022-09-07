package com.lzhch.applicationcontext.publishevent.event;

import org.springframework.context.ApplicationEvent;

/**
 *  自定义事件
 * @version: v1.0
 * @author: lzhch
 * @date: 2022/8/1 17:57
 */

public class SelfEvent extends ApplicationEvent {

    /**
     * 事件中需要在构造方法中调用父类的构造方法，将事件源传入。
     *
     * 也可以在构造方法中添加其他参数或者在事件源中添加其他方法，在监听类中可以调用相应方法，结合具体业务进行拓展。
     */
    public SelfEvent(Object source) {
        super(source);
    }

}
