
创建操作符 : 创建被观察者的各种操作符:(创建的这些被观察者进而可以配合组合操作符、转换操作符等)
       create() : 创建被观察则
       just() : (底层简介调用fromArray()),发送最多10个事件
       from类:  fromArray、fromFuture、fromIterable、fromCallable

       defer() : 这个方法的作用就是直到被观察者被订阅后才会创建被观察者。
       timer() : 当到指定时间后就会发送一个OL的值给观察者。
       interval() : 每隔一段时间就会发送一个事件，这个事件是从0开始，不断增1的数字。
       intervalRange() : 可以指定发送事件的开始值和数量，其他与interval()的功能一样。
       range() : 同时发送—定范围的事件序列。
       rangeLong() : 作用与range()一样，只是数据类型为Long
       empty() : 直接发送onComplete()事件
       never() : 不发送任何事件
       error() : 发送onError()事件


转换操作符 : 对事件流中的事件或者被观察者进行处理并转换出新的被观察者的一类操作符
       map() : map 可以将被观察者发送的数据类型转变成其他的类型
       flatMap() : 这个方法可以将事件序列中的元素进行整合加工，返回一个新的被观察者。

       concatMap() :  concatMap()和flatMap()基本上是一样的，只不过concatMap()转发出来的事件是 "有序的" ，而flatMap() "是无序的"。
       buffer() : 从需要发送的事件当中获取一定数量的事件，并将这些事件放到缓冲区当中一并发出
       compose() : 代码复用
       groupBy() : 将发送的数据进行分组，每个分组都会返回一个被观察者
       scan() : 将数据以一定的逻辑聚合起来
       window() : 发送指定数量的事件时，就将这些事件分为一组。window中的count 的参数就是代表指定的数量，例如将count 指定为2,那么每2


组合操作符 :
       concat() : 可以将多个观察者组合在一起，然后按照之前发送顺序发送事件。需要注意的是，concat()最多只可以发送4个事件。
       concatArray() : 与concat()作用一样，不过concatArray()可以发送多于4个被观察者。
       merge() : 这个方法月concat()作用基本一样，知识concat()是串行发送事件，而merge()并行发送事件。

       concatArrayDelayError() & mergeArrayDelayError() :
                   在concatArray)和mergeArray()两个方法当中，如果其中有一个被观察者发送了一个Error事件，那么就会停止发送事件，
                   如果你想 onError()事件延迟到所有被观察者都发送完事件后再执行的话，就可以使用concatArrayDelayError()和
                   mergeArrayDelayError()。
       zip() : 会将多个被观察者合并，根据各个被观察者发送事件的顺序一个个结合起来，最终发送的事件数量会与源Observable中最少事件的数量一样。
       combineLatest() & combineLatestDelayError()
       reduce()
       collect()
       startWith()
       startWithArray()
       count()

功能操作符 :
        subscribeOn() : 主要决定我执行subscribe方法所处于的线程，也就是产生事件发射所在的线程
        observeOn() : 主要决定下游事件被处理时所处于的线程

        delay()
        doOnEach()
        doOnNext()
        doAfterNext()
        doOnComplete()
        doOnError()
        doOnSubscribe()
        doOnDispose()
        doOnLifecycle()
        doOnTerminate() & doAfterTerminate()
        doFinally()
        onErrorReturn()
        onErrorResumeNext()
        onExceptionResumeNext()
        retry()
        retryUntil()
        retryWhen()
        repeat()
        repeatWhen()


过滤操作符

      filter() : 通过一定逻辑来过滤被观察者发送的事件，如果返回true则会发送事件，否则不会发送
      ofType() : 可以过滤不符合该类型事件
      skip() : 跳过正序某些事件，count代表跳过事件的数量
      distinct() : 过滤事件序列中的重复事件。
      distinctUntilChanged() : 过滤掉连续重复的事件
      take() : 控制观察者接收的事件的数量。
      debounce() : 如果两件事件发送的时间间隔小于设定的时间间隔则前一件事件就不会发送给观察者。
      firstElement() && lastElement() :  firstElement()取事件序列的第一个元素，lastElement()取事件序列的最后一个元素。
      elementAt() && elementAtOrError() :
                             elementAt()可以指定取出事件序列中事件，但是输入的index超出事件序列的总数的话就不会出现任何结果。
                             这种情况下，你想发出异常信息的话就用elementAtOrError()。

条件操作符
     all()
     takeWhile()
     skipWhile()
     takeUntil()
     skipUntil()
     sequenceEqual()
     contains()
     isEmpty()
     amb()
     defaultEmpty()


RxJava内存泄漏问题
     1、在页面销毁后，Observable仍然还有事件等待发送和处理，这个时候会导致Activity回收失败，从而致使内存泄漏。
     2、解决RxJava内存泄漏主要方式是在页面关闭之前取消所有的订阅:
           (1): 使用Disposable，关闭页面时调用dispose()取消订阅。
           (2): 使用CompositeDisposable，添加一组Disposable，在关闭页面时同时取消订阅。
     3、使用框架自定取消订阅，其原理是跟Activity生命周期进行绑定，在摧毁时取消订阅。




