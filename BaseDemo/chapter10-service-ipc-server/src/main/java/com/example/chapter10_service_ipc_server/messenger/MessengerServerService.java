package com.example.chapter10_service_ipc_server.messenger;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MessengerServerService extends Service {

    @SuppressLint("HandlerLeak")
    private Handler messengerHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            // 取出客户端的消息内容
            Bundle bundle = msg.getData();
            String clientMsg = bundle.getString("client");
            Log.d("test","来自客户端的消息: " + clientMsg);
            // 新建消息Message发送给客户端
            Message message = Message.obtain();
            Bundle bundle2 = new Bundle();
            bundle2.putString("service",clientMsg + ",服务端收到");
            message.setData(bundle2);
            // 新建一个Messenger对象,作为回复客户端的对象
            try {
                Messenger messenger = msg.replyTo;
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    // 创建服务器端Messenger
    private final Messenger mMessenger = new Messenger(messengerHandler);

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test","MessengerService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test","service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("test","service  onStart");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("test","MessengerService onBind");
        // 向客户端返回IBinder对象,客户端利用该对象访问服务器端
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("test","MessengerService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test","MessengerService onDestroy");
    }
}
