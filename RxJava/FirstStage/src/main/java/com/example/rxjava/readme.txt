
                                            Github地址
                                            关于对RxJava的选择
                                            RxJava简介
                                            RxJava优势及适用场景
                                            三个基本的概念
                        Rxjava介绍及使用      五种被观察者
                                            强大的操作符群          创建操作符
                                                                  转换操作符
                                                                  组合操作符
                                                                  功能操作符
                                                                  过滤操作符
                                                                  条件操作符
  RxJava2
                        RxJava核心实现        观察者模式or装饰者模式
                                             手写RxJava核心部分实现
                                             核心源码分析之创建操作符及转换操作符
                                             Scheduler线程调度       作用:简化了异步操作
                                                                        subscribeOn
                                                                        observeOn
                                                                        Scheduler种类
                                                                        线程调度原理分析:  subscribeOn,observeOn操作符分析
                                                                                         线程调度核心手写实现
                        RxJava最强拓展         Flowable背压(选讲)
                                              生命周期及内存泄漏问题
                                              RxJava衍生框架:RxBus，RxPermission等
                                              RxJava组合框架:Retrofit等

  RxJava简介
       1、RxJava:Reactive Extensions for the JVM，基于JVM的Rx。
       2、Reactive Extensions (Rx)原来是由微软提出的一个综合了异步和基于事件驱动编程的库。
          RxJava is a Java VM implementation of Reactive Extensions:
          A library for composing asynchronous and event-based programs by using observable sequences。
       3、RxJava的核心就是异步数据流和响应式编程:
          (1): 把所有的事件(数据)看成一条河流，它可以被观察、过滤或操作，也可以和另外一条河流汇合成新的一条的河流。
          (2): 一旦事件产生或发生变化，就可以触发观察这些事件的角色(观察者/订阅者)做出响应处理。


  RxJava优势及适用场景
       1、RxJava当然是优秀而且强大的，有以下优势:
           (1): 具备响应式编程该有的特性。
           (2): 为异步而生，无需手动创建线程，并具备线程切换能力。
           (3): 支持链式调用，保证代码的简洁性。
           (4): 各种操作符，功能非常强大，满足各种业务需求。
           (5): 简化了异常的处理。
       2、RxJava适用场景:网络请求、数据库读写、文件读写、定时任务等 各种耗时操作需要通过异步来完成的操作都可以使用RxJava。


  RxJava几个重要概念
       1、观察者:Observer，观察事件变化并处理的主要角色。消费者(Consumer)也可以理解成一种特殊的观察者。
       2、被观察者:触发事件并决定什么时候发送事件的主要角色。(异常和完成也是一种事件)
          (1): Observable、Flowable、Single、Completable、Maybe都是被观察者。
          (2): Flowable是支持背压的一种被观察者。
          (3): Single、Completable、Maybe是简化版的Observable。
          (4): 几种被观察者通过toObservable/toFlowable/toSingle/toCompletable/toMaybe相互转换。
       3、订阅(subscribe):观察者和被观察者建立关联的操作。






