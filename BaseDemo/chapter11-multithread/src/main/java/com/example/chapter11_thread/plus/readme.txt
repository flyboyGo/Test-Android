
1、多线程的意义

  (1): 为什么要用多线程
      a) 提高用户体验或者避免ANR
         在事件处理代码中需要使用多线程，否则会出现ANR(Application is not responding)，或者因为响应较慢导致用户体验很差。
      b)异步
         应用中有些情况下并不一定需要同步阻塞去等待返回结果，可以通过多线程来实现异步，例如你的应用中的某个Activity需要从云端获取一些图片,
         加载图片比较耗时，这时需要使用异步加载，加载完成一个图片刷新一个。
      c)多任务
         例如多线程下载。后两点与Java中的多线程应用没有太大区别，不细说。

   (2): 为什么通过多线程可以提高用户体验避免ANR

      a) 什么是ANR
        ANR全称Application Not Responding,意思就是程序未响应。如果一个应用无法响应用户的输入，系统就会弹出一个ANR对话框，
        如下图所示,用户可以自行选择继续等待亦或者是停止当前程序。

      b) 深入了解
        我们继续来了解一下Android应用程序的main线程，它负责处理UI的绘制，Android系统为了防止应用程序反应较慢导致系统无法正常运行做了一个处理，
        一种情况是当用户输入事件在5秒内儿法得到响应，那么系统会弹出ANR对话框，由用户决定继续等待还是强制结束应用程序
        (另一种情况是BroadcastReceiver超过10秒没执行完也会弹出ANR对话框)。

        即使你的程序中某个事件响应不超过5秒钟，人眼可以分辨的时间是0.1秒，小于0.1秒基本感觉不出来，超过0.2秒用户就能感觉到有点儿卡了，俗称打嗝现象，2秒以上就很慢了，用户体验会很差。
        有同学说我可以用进度条啊，但你的程序中不能到处都是进度条，否则那个圈圈会把用户转晕的，好像在对用户说，画个圈圈烦死你...

        比如某些应用，它要显示很多图片，还好它是异步的，不过在图片加载完成前每个图片的位置上都有一个圈圈，让人看了很烦。
        你可以变通一下，图片加载成功之前显示一个默认的图片，加载成功后再刷新一下即可，何必弄那么多进度条呢。

      c) 事件处理原则

        事件处理的原则∶所有可能耗时的操作都放到其他线程去处理。
        Android中的Main线程的事件处理不能太耗时，否则后续的事件无法在5秒内得到响应，就会弹出ANR对话框。
        那么哪些方法会在Main线程(主线程)执行呢?
         1): Activity的生命周期方法，例如:onCreate()、onStart()、onResume()等

         2): 事件处理方法，例如onClick()、onItemClick()等。

            通常Android基类中以on开头的方法是在Main线程被回调的。提高应用的响应性，可以从这两方面入手。
            一般来说，Activity的onCreate()、onStart()、onResume()方法的执行时间决定了你的应用首页打开的时间，
            这里要尽量把不必要的操作放到其他线程去处理，如果仍然很耗时，可以使用SplashScreen(引导页)。
            使用SplashScreen最好用动态的，这样用户知道你的应用没有死掉。

       d) 实际操作

          当用户与你的应用交互时，事件处理方法的执行快慢决定了应用的响应性是否良好，这里分两种情况∶
          1)同步，需要等待返回结果。例如用户点击了注册按钮，需要等待服务端返回结果，那么需要有一个进度条来提示用户你的程序正在运行没有死掉。
            一般与服务端交互的都要有进度条，例如系统自带的浏览器，URL跳转时会有进度条。

          2)异步，不需要等待返回结果。例如微博中的收藏功能，点击完收藏按钮后是否成功执行完成后告诉我就行了，我不想等它，这里最好实现为异步的。
            无论同步异步，事件处理都可能比较耗时，那么需要放到其他线程中处理，等处理完成后，再通知界面刷新。

           这里有一点要注意，不是所有的界面刷新行为都需要放到Main线程处理，例女TextView的setText()方法需要在Main线程中，
           否则会抛出CalledFromWrongThreadException，而ProgressBar的setProgress()方法则不需要在Main线程中处理。

          当然你也可以把所有UI组件相关行为都放到Main线程中处理，没有问题。可以减轻你的思考负担，但你最好了解他们之间的差别，掌握事物之间细微差别的是专家。
          把事件处理代码放到其他线程中处理，如果处理的结果需要刷新界面，那么需要线程间通讯的方法来实现在其他线程中发消息给Main线程处理。

   (3): 如何实现多线程之间的通讯

        a) Handler
           1) Handler
           2) Looper
           3) Message
           4) MessageQueue

        b) AsyncTask

           AsyncTask是Android框架提供的身步处理的辅助类，它可以实现耗时操作在其他线程执行，而处理结果在Main线程执行，
           对于开发者而言，它屏蔽掉了多线程和后面要讲的Handler的概念。你不了解怎么处理线程间通讯也没有关系，
           AsyncTask体贴的帮你做好了。不过封装越好越高级的API，对初级程序员反而越不利，就是你不了解它的原理。
           当你需要面对更加复杂的情况，而高级API无法完成得很好时，你就杯具了。所以，我们也要掌握功能更强大，更自由的与Main线程通讯的方法:Handler的使用。

   (4): 利用线程池提高性能

        这里我们建议使用线程池来管理临时的Thread对象，从而达到提高应用程序性能的目的。

        线程池是资源池在线程应用中的一个实例。了解线程池之前我们首先要了解一下资源池的概念。
        在JAVA中，创建和销毁对象是比较消耗资源的。我们如果在应用中需要频繁创建销毁某个类型的对象实例，这样会产生很多临时对象，
        当失去引用的临时对象较多时，虚拟机会进行垃圾回收(GC)，CPU在进行GC时会导致应用程序的运行得不到相应，从而导致应用的响应性降低。

        资源池就是用来解决这个问题，当你需要使用对象时，从资源池来获取，资源池负责维护对象的生命周期。
        了解了资源池，就很好理解线程池了，线程池就是存放对象类型都是线程的资源池。

2、多线程的创建

   (1): 继承Thread类实现多线程
        继承Thread类的方法尽管被我列为一种多线程实现方式，但Thread本质上也是实现了Runnable接口的一个实例，
        它代表一个线程的实例，并且启动线程的唯一方法就是通过Thread类的start()实例方法。start()方法是一个native(本地)方法，
        它将启动一个新线程，并执行run()方法。这种方式实现多线程很简单，通过自己的类直接extend Thread，并复写run(方法，
        就可以启动新线程并执行自己定义的run()方法。

   (2): 实现Runnable接口方式实现多线程
        如果自己的类已经extends另一个类，就无法直接extends Thread，此时必须实现一个Runnable接口。
        同时为了启动MyThread，需要首先实例化一个Thread，并传入自己的已经实现好Runnable接口的目标对象。


   (3): 实现Runnable和继承Thread的区别

         1，一个类只能继承一个父类，存在局限;一个类可以实现多个接口

         2，在实现Runnable接口的时候调用Thread(Runnable target)创建进程时,使用同一个Runnable实例，则建立的多线程的实例变量也是共享的。
            但是通过继承Thread类是不能用一个实例建立多个线程，故而实现Runnable接口适合于资源共享。
            当然，继承Thread类也能够共享变量，能共享Thread类的static变量;

         3，Runnable接口和Thread之间的联系︰
            public class Thread extends Object implements Runnable
            可以看出Thread类也是Runnable接口的子类;

3、线程池的应用

     (1): new Thread的弊端

          a.每次new Thread新建对象性能差。
          b.线程缺乏统一管理，可能无限制新建线程，相互之间竞争，及可能占用过多系统资源导致死机或oom。
          c.缺乏更多功能，如定时执行、定期执行、线程中断。

     (2): Java的线程池

          a.重用存在的线程，减少对象创建、消亡的开销，性能佳。
          b.可有效控制最大并发线程数，提高系统资源的使用率，同时避免过多资源竞争，避免堵塞。
          c.提供定时执行、定期执行、单线程、并发数控制等功能。

          1、newCachedThreadPool
             newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。

          2、newFixedThreadPool
             newFixedThreadPool创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。

          3、newSingleThreadExecutor
             newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO,优先级)执行。

          4、newScheduledThreadPool
              newScheduledThreadPool创建一个定长线程池，持定时及周期性任务执行。

4、异步消息处理机制

    (1): Handler
         Handler在android里负责 "发送" 和 "处理" 消息，通过它可以实现 "其他线程"与 "Main线程" 之间的消息通讯。

         send:
         1、sendEmptyMessage(int)
         2、sendMessage(Message)
         3、sendMessageAtTime(Message , long)
         4、sendMessageDelayed(Message ,long)
         sendMessage类方法允许你安排一个带数据的Message对象到队列中，等待处理。


         post:
         1、post(Runnable)
         2、postAtTime(Runnable, long)
         3、positDelayed(Runnable,long)

         post类方法允许你排列一个Runnable对象到主线程队列中，等待执行。

         总结
         1、传递Message。用于接受子线程发送的数据,并用此数据配合主线程更新UI,
            在Android中，对于UI的操作通常需要放在主线程中进行操作。如果在子线程中有关于UI的操作，那么就需要把数据消息作为一个Message对象发送到消息队列中，
            然后，用Handler中的handlerMessage方法处理传过来的数据信息，并操作UI。
            类sendMessage(Message msg)方法实现发送消息的操作。在初始化Handler对象时重写的handleMessage方法来接收Message并进行相关操作。

         2、传递Runnable对象。
             用于通过Handler绑定的消息队列，安排不同操作的执行顺序。Handler对象在进行初始化的时候，会默认的自动绑定消息队列。
             利用类post方法，可以将Runnable对象发送到消息队列中，按照队列的机制按顺序执行不同的Runnable对象中的run方法。


             还有以下几种方法可以在子线程中进行UI操作︰
             1、View的post()方法
             public boolean post(Runnable action)
             {
                    Handler handler;
                    if(mAttachInfo != null){
                          handler = mAttachInfo.mHandler; // 取到当前控件所依附线程(UI线程)的handler,将取到的handler赋给临时handler，进行相关操作
                    }
                    else {
                        iewRoot.getRunQueue0.post(action);
                        return true;
                    }
                    return handler.post(action);
             }

             2、Activity的runOnUiThread()方法
                 public final void runOnUiThread(Runnable action)
                 {
                     if (Thread.currentThread() != mUiThread) {
                          mHandler.post(action);
                     }
                     else {
                          action.run();
                     }
                 }

    (2): Looper
         Looper负责管理线程的消息队列和消息循环

    (3): Message
         Message是线程间通讯的消息载体。两个码头之间运输货物，Message充当集装箱的功能，里面可以存放任何你想要传递的消息。

    (4): MessageQueue
         MessageQueue是消息队列，先进先出，它的作用是保存有待线程处理的消息。

    它们四者之间的关系是，在其他线程中调用Handler.sendMsg(方法(参数是Message对象)，将需要Main线程处理的事件添加到Main线程的MessageQueue中，
    Main线程通过MainLooper从消息队列中取出Handler发过来的这个消息时，会回调Handler的handlerMessage()方法。


5、异步任务
   为了更加方便我们在子线程中更新UI元素，Android从1.5版本就引入了一个AsyncTask，使用它就可以非常灵活方便地从子线程切换到UI线程。

   AsyncTask
   AsyncTask:异步任务，从字面上来说，就是在我们的UI主线程运行的时候，异步的完成一些操作。AsyncTask允许我们的执行一个异步的任务在后台。
   我们可以将耗时的操作放在异步任务当中来执行，并随时将任务执行的结果返回给我们的UI线程来更新我们的UI控件。
   通过Async Task我们可以轻松的解决多线程之间的通信问题。

   怎么来理解AsyncTask呢?通俗一点来说，AsyncTask就相当于Android给我们提供了一个多线程编程的一个框架,其介于Thread和Handler之间,我们如果要定义一个
   AsyncTask,就需要定义一个类来继承AsyncTask这个抽象类,并实现其唯一的一个doInBackground抽象方法。


   (1): 用法
          首先来看一下AsyncTask的基本用法，由于AsyncTask是一个抽象类，所以如果我们想使用它，就必须要创建一个子类去继承它。
          在继承时我们可以为AsyncTask类指定三个泛型参数，这三个参数的用途如下:

        1.Params
          在执行AsyncTask时需要传入的参数，可用于在后台任务中使用。

        2.Progress
          后台任务执行时，如果需要在界面上显示当前的进度，则使用这里指定的泛型作为进度单位。

        3.Result
          当任务执行完毕后，如果需要对结果进行返回，则使用这里指定的泛型作为返回值类型。因此，一个最简单的自定义AsyncTask就可以写成如下方式:
          class DownloadTask extends AsyncTask<Void,Integer,Boolean> {
                 ...............
          }

   (2): 分析
          这里我们把AsyncTask的三个参数介绍:
          第一个泛型参数指定为Void，表示在执行AsyncTask的时候不需要传入参数给后台任务。
          第二个泛型参数指定为Integer，表示使用整型数据来作为进度显示单位。
          第三个泛型参数指定为Boolean，则表示使用布尔型数据来反馈执行结果。

          当然，目前我们自定义的DownloadTask还是一个空任务，并不能进行任何实际的操作，我们还需要去重写AsyncTask中的几个方法才能完成对任务的定制。
          经常需要去重写的方法有以下四个:

          1. onPreExecute()
             这个方法会在后台任务开始执行之前调用，用于进行一些界面上的初始化操作，比如显示一个进度条对话框等。

          2.doInBackground(Params...)
             这个方法中的所有代码都会在子线程中运行，我们应该在这里去处理所有的耗时任务。
             任务一旦完成就可以通过return语句来将任务的执行结果进行返回，如果AsyncTask的第三个泛型参数指定的是Void，就可以不返回任务执行结果。
             注意，在这个方法中是不可以进行UI操作的，如果需要更新UI元素，比如说反馈当前任务的执行进度，
             可以调用publishProgress(Progress...)方法来完成。

          3. onProgressUpdate(Progress...)
             当在后台任务中调用了publishProgress(Progress..)方法后，这个方法就很快会被调用，方法中携带的参数就是在后台任务中传递过来的。
             在这个方法中可以对UI进行操作，利用参数中的数值就可以对界面元素进行相应的更新。

          4. onPostExecute(Result)
              当后台任务执行完毕并通过return语句进行返回时，这个方法就很快会被调用。返回的数据会作为参数传递到此方法中，
              可以利用返回的数据来进行一些UI操作，比如说提醒任务执行的结果,以及关闭掉进度条对话框等。

         简单用法
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

6、AsyncTask和Handler对比

   (1): AsyncTask实现的原理和使用的优缺点

        AsyncTask,是android提供的轻量级的异步类,可以直接继承AsyncTask,在类中实现异步作,
        并提供接口反馈当前异步执行的程度(可以通过接口实现UI进度更新),最后反馈执行的果给UI主线程.

        使用的优点:
            简单,快捷,过程可控
        使用的缺点:
            在使用多个异步操作和并需要进行Ui变更时,就变得复杂起来.

   (2) Handler异步实现的原理和使用的优缺点
        在Handler异步实现时,涉及到Handler, Looper,Message,Thread四个对象，实现异的流程是主线程启动Thread (子线程),
        子线程运行并生成Message通过Handler发送去，然后Looper取出消息队列中的的Message再分发给Handler进行UI变更。

        使用的优点∶
            结构清晰，功能定义明确。对于多个后台任务时，简单，清晰
        使用的缺点∶
            在单个后台异步处理时，显得代码过多，结构过于复杂(相对性)





