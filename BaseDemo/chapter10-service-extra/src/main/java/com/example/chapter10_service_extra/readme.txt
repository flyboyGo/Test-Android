

1、如同对 Activity 及其他组件的操作一样，必须在应用的清单文件中声明所有服务。
   如要声明服务，请添加 <service> 元素作为 <application> 元素的子元素。
   可以通过添加 android:exported 属性并将其设置为 false，确保服务仅适用于当前的应用。
   这可以有效阻止其他应用启动当前应用的服务，即便在使用显式Intent 时也如此。

2、注意:
    onStartCommand()
    当另一个组件（如 Activity）请求启动服务时，系统会通过调用 startService()来调用此方法。
    执行此方法时，服务即会启动并可在后台无限期运行。
    如果实现此方法，则在服务工作完成后，需通过调用 stopSelf() 或 stopService() 来停止服务。(如果只想提供绑定，则无需实现此方法。)

    @Override
    public int startCommand(Intent intent,int flags, int startId){
    }

    int flags的取值的可能(高版本做出了限制,实际无需关注,直接使用默认值)
    (1): START_STICKY：如果 service 进程被 kill 掉，则保留 service 为开始状态，
         但不保留传递的 intent 对象，随后系统尝试重新创建 service，因为是开始状态，所以
         在创建后一定会调用 onStartCommand(Intent, int, int)方法，
         如果在此期间没有任何其他启动命令被传递到 service，则 intent 为 null。

    (2): START_NOT_STICKY：非粘性，意思是如果在执行完 onStartCommand(Intent, int,int)方法后，
         若 service 被系统异常 kill 掉，系统不会自动重启该 service。

    (3): START_REDELIVER_INTENT：重传 Intent，使用这个返回 flags 值时，
         如果在执行完 onStartCommand(Intent, int, int)后，service 被系统异常 kill 掉，系统会重启该
         service，并将 intent 传入。

    (4): START_STICKY_COMPATIBILITY：START_STICKY 的兼容版本，但不保证 service一定会被重启。

    int startId的含义
      一个服务可能被启动多次(startCommand启动服务),每一次启动都会生成不同的startId

3、启动 Service
    (1): startService()
         如果组件通过调用 startService() 启动服务(这会引起对 onStartCommand()的调用),
         则服务会一直运行，直到其使用 stopSelf() 自行停止运行，或由其他组件通过调用 stopService() 将其停止为止。
    (2):
         bindService()
         如果组件通过调用 bindService() 来创建服务，且未调用 onStartCommand()，则服务只会在该组件与其绑定时运行。
         当该服务与其所有组件取消绑定后，系统便会将其销毁

         进程间通讯
         扩展 Binder 类
         如果服务是提供给自有应用专用的，并且Service(服务端)与客户端相同的进程中运行（常见情况），则应通过扩展 Binder 类并从 onBind() 返回它的一个实例来创建接口。
         客户端收到 Binder 后，可利用它直接访问 Binder 实现中以及Service 中可用的公共方法。
         如果我们的服务只是自有应用的后台工作线程，则优先采用这种方法。 不采用该方式创建接口的唯一原因是，服务被其他应用或不同的进程调用。

    (3): 二者结合使用
         startService(),先启动Service(服务),其次bindService()绑定服务,将对应的组件绑定到该服务上。
         1、如果此时调用stopService()、stopSelf()是无法停止服务的(调用服务onDestroy()),因为有组件正在绑定在其上面,需要先解绑,再停止。
         2、如果此时调用unBind(),组件解绑定Service,Service不会立即停止(调用服务onDestroy(),系统内存不足时可能被回收),
            因为该服务是先启动onStartCommand(),组件后绑定(bindService()),类似于服务器-客户端的效果,不是只用于该组件。

    注意: 为确保应用的安全性，在启动 Service 时，请始终使用显式 Intent，且不要为服务声明 Intent 过滤器。
         使用隐式 Intent 启动服务存在安全隐患，因为您无法确定哪些服务会响应 Intent，而用户也无法看到哪些服务已启动。

4、IntentService(继承自Service,其中包含了一个工作线程(子线程),去执行耗时任务)
     概述:
        它本质是一种特殊的Service,继承自Service并且本身就是一个抽象类
        它可以用于在后台执行耗时的异步任务，当任务完成后会自动停止
        它拥有较高的优先级，不易被系统杀死（继承自Service的缘故），因此比较适合执行一些高优先级的异步任务
        它内部通过HandlerThread和Handler实现异步操作
        创建IntentService时，只需实现onHandleIntent和构造方法，onHandleIntent为异步方法，可以执行耗时操作

   Service 不是一个单独的进程,它和应用程序在同一个进程中。Service 不是一个子线程,应该避免在 Service 中进行耗时操作。
   Android 给我们提供了解决上述问题的一种方案，就是 IntentService;
   IntentService 是继承与 Service 并处理异步请求的一个类,在 IntentService 中 "有一个工作线程来处理耗时操作" ,请求的 Intent 记录会加入队列。
   其他组件通过startService(Intent)来启动IntentService; 我们并不需要手动地去控制IntentService,当任务执行完后,IntentService会自动停止;
   可以启动IntentService多次,每个耗时操作会以工作队列的方式在IntentService的onHandleIntent 回调方法中执行,
   并且每次只会执行一个工作线程,执行完一个，再到下一个

      Service -----> onCreate()   ------>  执行       ------->   新     ------>  无    ------->  Service
       start         HandlerThread      onHandleIntent        Intent                              stop
                                             ^                   |
                                             |                   有
                                             |                   |
                                             |-----执行下一个<-----|
                                                   intent请求
   这里要注意的是如果后台任务只有一个的话，onHandleIntent执行完，服务就会销毁，但如果后台任务有多个的话，onHandleIntent执行完最后一个任务时，服务才销毁。
   最后我们要知道每次执行一个后台任务就必须启动一次IntentService，而IntentService内部则是通过消息的方式发送给HandlerThread的，
   然后由Handler中的Looper来处理消息，而Looper是按顺序从消息队列中取任务的，也就是说IntentService的后台任务时顺序执行的，
   当有多个后台任务同时存在时，这些后台任务会按外部调用的顺序排队执行


5、JobIntentService(默认是绑定启动的)
   JobIntentService 与 IntentService 非常相似，但没有什么好处，
   例如应用程序可以随时终止此工作，并且一旦应用程序重新创建/启动，它就可以继续工作了。


6、JobService
       JobService 继承 Service，适用于需要特定条件下才执行后台任务的场景。
       由系统统一管理和调度，在特定场景下使用 JobService 更加灵活和省心，相当于是Service 的加强或者优化。
       由系统统一管理和调度，在特定场景下(网络状态等等)使用 JobService 更加灵活和省心。

  JobService 的方法:
       (1): onStartJob(): onStartJob()的回调在 UI 线程，不可执行耗时逻辑，否则可能造成 ANR 或者 Job 被强制销毁(超过 8s)。
            并且，JobService 里即便新起了线程，处理的时间也不能超过 10min，否则 Job 将被强制销毁。
       (2): jobFinished(): Job 的任务执行完毕后，APP 端主动调用，用以通知 JobScheduler已经完成了任务。
             该方法执行完后会回调 onDestroy()。
       (3): onStopJob(): 停止该 Job。当 JobScheduler 检测 Job 条件不满足的时候，或者job 被抢占被取消的时候的强制回调。
            返回 true，即可在被强制停止后再度启动起来

  JobService用于执行一些需要满足特定条件但不紧急的后台任务，利用 JobScheduler 来执行这些特殊的后台任务来减少电量的消耗。
  开发者可以设定需要执行的任务JobService，以及任务执行的条件 JobInfo，JobScheduler 会将任务加入到队列。
  在特定的条件满足时 Android 系统会去批量的执行所有应用的这些任务，而非对每个应用的每个任务单独处理。这样可以减少设备被唤醒的次数。






7、前台 Service
   Service 一般都是运行在后台的，但是 Service 的系统优先级还是比较低的，
   当系统内存不足的时候，就有可能回收正在后台运行的 Service， 对于这种情况我
   们可以使用前台服务，从而让 Service 稍微没那么容易被系统杀死， 当然还是有
   可能被杀死的，所谓的前台服务就是状态栏显示的 Notification！

8、关于启动服务与绑定服务间的转换问题
   (1): 通过前面对两种服务状态的分析，相信大家已对Service的两种状态有了比较清晰的了解，那么现在我们就来分析一下当启动状态和绑定状态同时存在时，又会是怎么的场景？
   (2): 虽然服务的状态有启动和绑定两种，但实际上一个服务可以同时是这两种状态，也就是说，它既可以是启动服务（以无限期运行），也可以是绑定服务。
        有点需要注意的是Android系统仅会为一个Service创建一个实例对象，所以不管是启动服务还是绑定服务，操作的是同一个Service实例，
        而且由于绑定服务或者启动服务执行顺序问题将会出现以下两种情况：

   最后这里有点需要特殊说明一下的，由于服务在其托管进程的主线程中运行（UI线程），它既不创建自己的线程，也不在单独的进程中运行（除非另行指定）。
   这意味着，如果服务将执行任何耗时事件或阻止性操作（例如 MP3 播放或联网）时，则应在服务内创建新线程来完成这项工作，简而言之，耗时操作应该另起线程执行。
   只有通过使用单独的线程，才可以降低发生“应用无响应”(ANR) 错误的风险，这样应用的主线程才能专注于用户与 Activity 之间的交互， 以达到更好的用户体验。

9、
    服务的整个生命周期从调用 onCreate() 开始起，到 onDestroy() 返回时结束。与 Activity 类似，服务也在 onCreate() 中完成初始设置，并在 onDestroy() 中释放所有剩余资源。
    例如，音乐播放服务可以在 onCreate() 中创建用于播放音乐的线程，然后在 onDestroy() 中停止该线程。

    (1)、无论服务是通过 startService() 还是 bindService() 创建，都会为所有服务调用 onCreate() 和 onDestroy() 方法。
    (2)、服务的有效生命周期从调用 onStartCommand() 或 onBind() 方法开始。每种方法均有 Intent 对象，该对象分别传递到 startService() 或 bindService()。
    (3)、对于启动服务，有效生命周期与整个生命周期同时结束（即便是在 onStartCommand() 返回之后，服务仍然处于活动状态）。对于绑定服务，有效生命周期在 onUnbind() 返回时结束。

10、JobScheduler


