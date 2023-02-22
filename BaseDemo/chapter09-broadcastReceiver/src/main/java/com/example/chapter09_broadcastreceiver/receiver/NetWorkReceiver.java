package com.example.chapter09_broadcastreceiver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.util.Log;

import com.example.chapter09_broadcastreceiver.util.NetworkUtil;

public class NetWorkReceiver extends BroadcastReceiver {

    /*
        因为onReceive方法只表示收到了网络广播，至于变成哪种网络，还得把广播消息解包才知道是怎么回事。

        网络广播携带的包裹中有个名为networkInfo的对象，其数据类型为NetworkInfo，
        于是调用NetworkInfo对象的相关方法，即可获取详细的网络信息。
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            NetworkInfo networkInfo = intent.getParcelableExtra("networkInfo");
            // 收到一个网络变更广播，网络大类为MOBILE，网络小类为HSPA，网络制式为3G，网络状态为DISCONNECTED
            // 收到一个网络变更广播，网络大类为WIFI，网络小类为，网络制式为未知，网络状态为CONNECTED
            String text = String.format("收到一个网络变更广播，网络大类为%s，" +
                                        "网络小类为%s，网络制式为%s，网络状态为%s",
                    networkInfo.getTypeName(),
                    networkInfo.getSubtypeName(),
                    NetworkUtil.getNetworkClass(networkInfo.getSubtype()),
                    networkInfo.getState().toString());
            Log.d("test", text);
        }
    }
}
