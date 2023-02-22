package com.example.chapter09_broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chapter09_broadcastreceiver.receiver.OrderAReceiver;
import com.example.chapter09_broadcastreceiver.receiver.OrderBReceiver;

public class BroadOrderActivity extends AppCompatActivity implements View.OnClickListener {

    public static  final  String ORDER_ACTION = "com.example.chapter09_broadcastreceiver.order";
    private OrderAReceiver orderAReceiver;
    private OrderBReceiver orderBReceiver;

    /*
         有序广播
         一个广播存在多个接收器，这些接收器需要排队收听广播，这意味着该广播是条有序广播。
         先收到广播的接收器A，既可让其他接收器继续收听广播，也可中断广播不让其他接收器收听。
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_order);

        findViewById(R.id.btn_send_standard_broad).setOnClickListener(this);
    }

    /*
    第一个参数，不用说了，我们发送广播就知道了，这个是意图对象，用于封装数据和设置过滤。
    第二个参数是权限，权限我们后面会详细说到，虽然很少用到，但是你知道这个思想，有利于你以后自己钻研android的代码。
    第三个参数是广播接收者，这个广播接收者是最终接收的广播接收者，用于检查数据是否有传达或者数据被修改。
    第四个参数是一个自定义的Handler，用于处理结果接收者，也就是上面那个接收者的回调。
    第五个参数是初始码，这个会作为结果码，通常是Activity.RESULT_OK，也就是-1。
    第六个参数是用于传递数据的，这个数据在各个Receiver里获取到，通过getResultData方法获取。这个其实通常为null
    第七个参数也是用于封装数据的，不同的是，这个用于封装数据集合，从上面的代码可以知道 ，我用来封装了一个钱的数据。
     */
    @Override
    public void onClick(View v) {
        // 创建指定动作的意图
        Intent intent = new Intent(ORDER_ACTION);
        Bundle bundle = new Bundle();
        bundle.putString("msg","有序广播携带的数据,未修改");
        //intent.putExtras(bundle);

        // 发送有序广播
        //sendOrderedBroadcast(intent,null);

        // 新的方式发送广播,并携带数据
        sendOrderedBroadcast(intent,null,null, null, Activity.RESULT_OK,"数据未修改", bundle);
        Toast.makeText(this, "发送有序广播成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 多个接收器处理有序广播的顺序规则
        // 1、优先级越大的接收器，越早收到有序广播
        // 2、优先级相同的时候，越早注册的接收器越早收到有序广播

        orderAReceiver = new OrderAReceiver();
        IntentFilter filterA = new IntentFilter(ORDER_ACTION);
        // 设置广播的优先级(-1000 ~ 1000, 默认为0)
        filterA.setPriority(8);
        registerReceiver(orderAReceiver,filterA);

        orderBReceiver = new OrderBReceiver();
        IntentFilter filterB = new IntentFilter(ORDER_ACTION);
        // 设置广播的优先级
        filterB.setPriority(9);
        registerReceiver(orderBReceiver,filterB);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(orderAReceiver);
        unregisterReceiver(orderBReceiver);
    }
}