
广播Broadcast概念
   1、广播Broadcast是Android四大组件之一。
   2、是用来互相通信（传递信息）的一种机制。
       (1): 四大组件间通信（应用内部,一个APP）
       (2): 进程间通信    (多个APP之间)

广播Broadcast的组成部分
   1、广播发送者:Activity、Service等
   2、广播接收者:BroadcastReceiver

      发送者   --->   Intent --->  BroadcastReceiver

广播Broadcast的基本使用方式
      1.广播发送
           与启动其他四大组件（Activity、Service）一样，也是使用Intent发送广播。
           (1): 通过设置Action，标识该广播可被哪些接收器收到。
           (2): 可通过putExtra，传递额外的bundle信息。


           创建Intent
           Intent intent= new Intent();
           intent.setAction(ACTION_UPLOAD_RESULT);
           intent.putExtra(KEY_RESULT, filePath);
           发送广播
           sendBroadcast(intent):

      2.广播接收
           (1): 使用到了BroadcastReceiver类，继承或者直接new该类的对象实例，重写方法onReceive在该方法中处理收到的广播消息。

           private BroadcastReceiver mReceiver = new BroadcastReceiver() {
               @Override
               public void onReceive(Context context,Intent intent) {
                    /收到广播消息
               }
           };

           (2): 创建完广播接收器后，最后还需要将其注册，这样它才能接收广播。
                为其配置Action，表示它能接受的广播特征，从而过滤到适合它的广播信息。


      3、静态广播与动态广播的概念
         按广播接收器的注册方式划分的

           1、静态广播，是在Manifest文件中注册的广播。
              常驻内存中，可在App未启动时就监听广播，如监听短信、系统时间等系统广播事件。

           2、动态广播，是在代码中注册以及解除注册的广播。
                App启动后注册，然后才能监听。解除注册（一般是Activity销毁时）后广播也随之结束。

            注意:同时注册时，动态广播优先于静态广播。


      4、静态广播的注册与使用

            Android 8.0及以上禁止了后台执行，因此无法收到静态注册的隐式广播。
            错误日志: W/BroadcastQueue:Background execution not allowed: receiving Intent

            解决方法:
            1.发送广播时改为显式广播:intent.setPackage(getPackageName())
            2.添加可后台执行的flag: intent.addFlags(Ox01000000)
            public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = Ox0100000o;


            一些系统广播不受该限制，如:开机完成、设置系统时间、设置当前时区等。
            详细见:https://developer.android.google.cn/guidelcomponents/broadcast-exceptions


       5、无序广播与有序广播的区别

            按广播接收顺序划分
            1. 无序广播(默认发送的广播就是无序广播)，又叫普通广播。所有的接收器接收的广播没有 先后顺序，几乎同时收到消息。
            2. 有序广播，发送的广播会按接收器的优先级顺序被接收。同一时刻只会有一个接收器收到广播，且可以对广播截断，修改。

       6、截断有序广播
            在广播接收器里调用abortBroadcast()方法即可阻断有序广播的传递。


       7、修改有序广播
            方法一:
            1. 通过setResultExtras(bundle)方法，向下游广播接收器传递额外键值对儿信息。
            2. 下游广播接收器通过getResultExtras(boolean)方法接收信息。

            方法二：
            通过setResultData(str)方法，向下游广播接收器传递额外字符串信息。
            下游广播接收器通过getResultData方法接收信息。


       8、本地广播与全局广播的区别

            按广播传播范围划分
            (1) : 本地广播，仅在本App内部传播，其他App收不到,保证了数据的安全性。
            (2) : 全局广播，可以在整个手机所有App之间传播，会有安全性问题。普通广播 默认就是全局广播。

       9、本地广播的使用-广播接收器的创建(动态注册)
            (1) : 广播接收器的创建，与正常广播一样，继承BroadcastReceiver
            (2) : 本地广播的使用-广播接收器的注册
                  只能使用动态注册的方式。这里用到了一个类: LocalBroadcastManager

            注:
              (1) : LocalBroadcastManager位于androidx.localbroadcastmanager:localbroadcastmanager:1.1.0中，如果引用不到，需要手动添加该依赖。
              (2) : LocalBroadcastManager已经被官方废弃，此处仅作了解。
              (3) : 详见<https://developer.android.google.cn/jetpacklandroidx/releases/localbroadcastmanager>


       10、本地广播的使用-广播接收器的解注册与发送
           (1) : 解除与发送也比较简单，使用LocalBroadcastManager调用原来的方法即可。


        11、全局广播的使用
            (1) : 前面学习到的普通广播默认就是全局的，可以被其他app接收到，不再赘述。






