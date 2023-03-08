
   Handler
   1、概述
     (1):是什么? Android中处理异步消息的核心类
     (2):做什么? 处理延时任务,推送未来某个时间点将要执行的Message或者Runnable到消息队列中;
                线程间通信,在子线程把需要在另一个线程执行的操作加入到消息队列中;
   2、结构
      类结构:Handler、Looper、MessageQueue、Message、Thread(环境)

   3、注意
       使用handler发送消息,handler会将该消息放入消息队列MessageQueue(小哥取件完成),Looper启动并根据消息队列中消息的时间来决定分发的时间(快递分拣)
       取出消息并分发至handler进行消息处理（这里有点不同的是，发送及处理消息的handler是同一个，但同一个快递的取件小哥和送件小哥一般不是同一个，
       handler在将消息放入队列当中时将自身的引用也放至消息中，在分发处理时保证了同一个handler来处理该消息)

       想要发送消息，势必要有储存消息的消息队列及循环处理分发消息的Looper;handler在使用过程中是存在多线程交互的,
       这意味着,handler在其他任意子线程中发送的消息都会被存储到当前线程操作的消息队列中,
       **第一个重点：**MessageQueue与Looper的唯一性;这个唯一性由ThreadLocal来确定looper的唯一,由looper的唯一来确定MessageQueue的唯一这就保证了它的正常工作基础。
       **第二个重点：**Looper中有循环对消息队列取消息的操作，这是个死循环；这个死循环的阻塞需要由MessageQueue完成。

  4、补充
     (1):ThreadLocal
        ThreadLocal是为解决多线程程序的并发问题的一种解决方案
        它为每个使用该变量的线程提供独立的变量副本，每个线程都可以独立修改自己的副本并不会影响其它线程所对应的副本
        可以简单将ThreadLocal当成一种特殊的HashMap，它的key固定为Thread，通过get和set方法来对Value做修改

     (2):主线程使用handler不用Looper准备和启动的原因: 在ActivityThread的Main方法中已经准备和启动了!
         Looper.prepareMainLooper();
         Looper.loop();

     (3):自定义Handler中死循环中阻塞会导致应用无响应，为什么主线程中Looper死循环不会?
         主线程loop在取不到消息时会 退出循环，继而抛出throw new RuntimeException("Main thread loop unexpectedly exited");
         异常,退出应用，所以说应用在运行时，主线程的loop时会不断的接收消息处理消息的。
         Android整体的交互都是有事件驱动的，looper.loop会不断的接收事件处理事件，如果它停止了应用也就停止了，
         所以一旦出现了ANR，说明是某个消息或者对某个消息的处理超时，阻塞了Looper.loop，从而造成ANR。
         自定义中死循环阻塞超时导致了主线程的loop阻塞超时引起ANR
         主线程中死循环正常运行是程序正常运行，相反它的死循环被阻塞超时则会引起ANR

     (4):子线程能更新UI的特殊情况:
           :onResume执行之前更新UI,主线程更新UI的检测是通过ViewRootImp的checkThread方法来检查的;ViewRootImpl是在onResume之后创建的
           :surfaceView在子线程刷新不会阻塞主线程，适用于界面频繁更新、对帧率要求较高的情况：相机预览，游戏

     (5):Handler使用当中的补充
         :Message的发送优化,享元设计模式，使用obtainMessage 方便回收复用Message，避免新建太多耗费内存
         :子线程创建Handler时需要准备Looper
         :注意Handler使用不当造成的OOM(内存泄露)
         :注意使用Handler容易造成空指针异常,handler在接收到消息进行处理时可能页面已经被销毁，引用资源找不到


     HandlerThread(Android的专属类)
     1、特点
        HandlerThread本质上是一个线程类，它继承了Thread;
        HandlerThread有自己的内部Looper对象，可以进行looper循环；
        通过获取HandlerThread的looper对象传递给Handler对象，可以在handleMessage方法中执行异步任务。
        创建HandlerThread后必须先调用HandlerThread.start()方法，Thread会先调用run方法，创建Looper对象。
     2、使用步骤
        (1):创建实例对象,传入参数的作用主要是标记当前线程的名字,可以任意字符串。
            HandlerThread handlerThread = new HandlerThread("downloadImage");
        (2):启动HandlerThread线程,必须先开启线程
            handlerThread.start();
        (3):构建循环消息处理机制,该callback运行于子线程
            class ChildCallback implements Handler.Callback {
                 @Override
                 public boolean handleMessage(Message msg) {
                     //在子线程中进行相应的网络请求等耗时操作
                     //通知主线程去更新UI
                      mUIHandler.sendMessage(msg1);
                      return false;
                 }
            }
        (4):构建异步handler,子线程Handler
            Handler childHandler = new Handler(handlerThread.getLooper(),new ChildCallback());

        第3步和第4步是构建一个可以用于异步操作的handler,并将前面创建的HandlerThread的Looper对象以及Callback接口类作为参数传递给当前的handler，
        这样当前的异步handler就拥有了HandlerThread的Looper对象，由于HandlerThread本身是异步线程，因此Looper也与异步线程绑定，
        从而handlerMessage方法也就可以异步处理耗时任务了，这样我们的Looper+Handler+MessageQueue+Thread异步循环机制构建完成。







