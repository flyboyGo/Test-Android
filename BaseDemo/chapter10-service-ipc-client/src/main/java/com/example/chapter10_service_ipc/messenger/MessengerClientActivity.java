package com.example.chapter10_service_ipc.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.chapter10_service_ipc.R;

public class MessengerClientActivity extends AppCompatActivity {

    private int messageId = 0;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            Log.d("test","收到远程服务数据: " + bundle.getString("service"));
        }
    };

    // 客户端Messenger,发送消息给服务器端
    private Messenger clientMessenger = new Messenger(handler);


    // 服务器端Messenger的代理,接收消息来自于服务器端
    private Messenger messengerProxy;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.d("test","onServiceConnected: ");
            messengerProxy = new Messenger(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("test","onServiceDisconnected: ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_client);
    }

    public void bindRemoteService(View view) {

        // 跨进程，不同应用中
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.chapter10_service_ipc_server",
                "com.example.chapter10_service_ipc_server.messenger.MessengerServerService"));

        boolean isBind = bindService(intent,connection, BIND_AUTO_CREATE);
        Log.d("test","bindRemoteService isBind = " + isBind);
    }

    public void sendData(View view) {
        if(messengerProxy == null){
            return;
        }
        Message message = new Message();
        message.replyTo = clientMessenger;
        Bundle bundle = new Bundle();
        bundle.putString("client","这是客户端发送的数据: " + messageId++);
        message.setData(bundle);
        try {
            messengerProxy.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unBindRemoteService(View view) {
        if(messengerProxy != null){
            unbindService(connection);
            messengerProxy = null;
            Log.d("test","unBindRemoteService");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (messengerProxy != null){
            unbindService(connection);
            messengerProxy = null;
            Log.d("test","onDestroy");
        }
    }
}