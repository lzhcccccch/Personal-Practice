## Spring 事件推送publishEvent

[toc]

#### 一、 简介

ApplicationContext.publishEvent 是 Spring 提供的解耦的一种方式，在 org.springframework.context 包下。

如果项目规模较大，已经引入了 MQ、XxlJob 等中间件，可以使用中间件替代。

默认是同步操作的，也就是说发布完之后需要等监听执行完毕才可以；异步需要单独开启，加 @Async 注解即可。

---

---

#### 二、 原理

本质上是设计模式中的观察者模式。

事件模型有三个组成部分：事件源 source（被监听对象）、事件 event 和监听对象 listener。

#### 三、 使用

简单示例代码

##### 1. 定义事件

事件中需要在构造方法中调用父类的构造方法，将事件源传入。

也可以在构造方法中添加其他参数或者在事件源中添加其他方法，在监听类中可以调用相应方法，结合具体业务进行拓展。

```java
public class SelfEvent extends ApplicationEvent {

    public SelfEvent(Object source) {
        super(source);
    }

}
```

---

##### 2. 定义事件源

事件源就是要监听的对象，比如要记录系统登录信息，那就定义一个对象以及表来存储信息，定义的对象就是事件源。

本文以 Map 为例。

---

##### 3. 定义监听对象

监听对象有两种形式：一种是类定义，就是针对一个事件用一个监听类；另一种是基于注解实现，该方式可以针对一类业务，即一个监听类中可以监听多个事件。

```java
@Component
public class SelfEventListener implements ApplicationListener<SelfEvent> {

  	/**
     *  类定义方式 只能针对于一个事件进行处理
     */
  
    @Override
    @Async
    public void onApplicationEvent(SelfEvent selfEvent) {
        HashMap<String, Object> source = (HashMap<String, Object>) selfEvent.getSource();

        System.out.println("类形式监听: {} " + source.toString());
    }

}
```

```java
@Component
public class AnnotationEventListener {

    /**
     * 基于注解实现的监听 可以在一个监听类中实现对多个事件的监听 适合某一类业务
     * 比如: 记录日志信息, 有多种日志格式, 多个事件, 但是是同一类业务
     */
    
    @Async
    @EventListener(SelfEvent.class)
    public void typeOne(SelfEvent event) {
        HashMap<String, Object> source = (HashMap<String, Object>) event.getSource();

        System.out.println("基于注解的监听 :{} " + source.toString());
    }

    // 可以根据 typeOne 进行对其他事件进行监听 建议根据业务分类:比如不同的日志可以用同一个监听类
    public void typeTwo() {
        return;
    }

}
```

---

##### 4. 统一发送事件

定义统一发送事件类，相当于工厂，可以在里面进行一些拓展。

```java
@Component
public class SelfEventPublisher {

    @Resource
    private ApplicationContext applicationContext;

    // 直接以 ApplicationContext 为入参 统一发布路径
    public void publish(ApplicationEvent applicationEvent) {
        applicationContext.publishEvent(applicationEvent);
    }

}
```

---

##### 5. 测试

```java
@RestController
@RequestMapping("/publishEvent")
public class PublishEventController {

    @Resource
    private SelfEventPublisher eventPublisher;

    @PostMapping("/classListener")
    public void classListener(@RequestParam Map<String, Object> params) {
        this.eventPublisher.publish(new SelfEvent(params));
    }

    @PostMapping("/annotationListener")
    public void annotationListener(Map<String, Object> params) {
        this.eventPublisher.publish(new SelfEvent(params));
    }

}
```

---

---





