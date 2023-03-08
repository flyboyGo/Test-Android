
  Android中使用跨进程通信有以下几种方式: Intent/bundle、文件(两个进程使用同一个外部公共存储空间中的文件)、广播、aidl、messenger、contentProvider、socket
  其中，使用 Messenger 和 AIDL 方式需要配合 Service 使用。

1、Service跨进程通信
   进程间的数据交互、通信，我们常称作跨进程通信，简称 IPC（Inter-Process Communication）。
   Android 跨进程间通信的方式主要有以下几种：
   1、使用 Intent/Bundle
   2、使用 文件共享
   3、使用 Messenger
   4、使用 AIDL
   5、使用 ContentProvider
   6、使用 Socket
   其中,使用 Messenger 和 AIDL 方式需要配合 Service 使用。
   Messenger 和 AIDL 如何选择呢？只有在需要不同应用的客户端通过 IPC 方式访问服务，并且希望在服务中进行多线程处理时，才有必要使用 AIDL;
   如果想执行 IPC，但不需要处理多线程，可以使用 Messenger 来实现接口。

2、IPC 的 Messenger 实现
   Messenger,即进程间通信的信使。它是基于 Message 的进程间通信,我们可以像在线程间利用 Handler.send(Message)一样。
   Messenger 是一种轻量级的 IPC 方案,它的底层实现其实就是 AIDL.跨进程通信使用 Messenger时,Messenger会将所有服务调用加入队列,然后服务端那边一次处理一个调用。

   服务端实现一个 Handler,由其接收来自客户端的每个调用的回调；
   服务端使用 Handler来创建 Messenger对象；
   Messenger创建一个 IBinder,服务端通过 onBind()将其返回给客户端;


   客户端使用IBinder将Messenger实例化,然后再用起将Message对象发送给服务器端;
   服务端在其 Handler#handleMessage()中,接收每个 Message;

   Messenger可以翻译为信使，通过它可以在不同的进程中共传递Message对象(Handler中的Message，因此 Handler 是 Messenger 的基础)，
   在Message中可以存放我们需要传递的数据，然后在进程间传递。
   如果需要让接口跨不同的进程工作，则可使用 Messenger 为服务创建接口，客户端就可利用 Message 对象向服务发送命令。

   同时客户端也可定义自有 Messenger，以便服务回传消息。
   这是执行进程间通信 (IPC) 的最简单方法，因为 Messenger 会在单一线程中创建包含所有请求的队列，也就是说Messenger是以串行的方式处理客户端发来的消息，
   这样我们就不必对服务进行线程安全设计了。

3、











