
   事件总线（Event Bus）是一种常用的 Android 应用程序设计模式，用于实现组件之间的解耦和通信。
   通过事件总线，不同组件可以发布和订阅事件，从而实现彼此之间的通信，而不需要直接引用彼此的对象。
   常见的事件总线框架包括 EventBus 和 RxJava 等。

   添加 EventBus 依赖库：在项目的 build.gradle 文件中添加以下依赖:
   implementation 'org.greenrobot:eventbus:3.2.0'


   这里的 onMessageEvent 方法是一个被 @Subscribe 注解标记的公共方法，用于处理订阅的 MessageEvent 事件。
   当事件发布时，EventBus 就会自动调用该方法来处理事件。

   需要注意的是，被 @Subscribe 注解标记的方法必须是公共方法，并且只能有一个参数，参数类型为订阅的事件类型。
   另外，被 @Subscribe 注解标记的方法可以指定线程模式（ThreadMode），用于指定事件处理方法运行的线程。
   默认情况下，事件处理方法会在发布事件的线程中执行。如果需要在主线程中执行事件处理方法，可以指定 ThreadMode.MAIN。
   如果需要在后台线程中执行事件处理方法，可以指定 ThreadMode.BACKGROUND。

   当 ActivityA 发送 MessageEvent 事件时，EventBus 会自动将事件发送给已经注册的订阅者（即 ActivityB），并调用其对应的事件处理方法。
   由于 ActivityB 已经在 onCreate 方法中注册了订阅者，并实现了 onMessageEvent 方法来处理 MessageEvent 事件，因此可以接收并处理来自 ActivityA 的事件消息。
