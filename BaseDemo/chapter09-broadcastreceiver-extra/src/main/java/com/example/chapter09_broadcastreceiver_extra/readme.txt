
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




