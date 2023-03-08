package com.example.navigationui.base.fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.navigationui.R;

public class MainFragment extends Fragment {


    private int notificationId;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = getView().findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }


    private void sendNotification() {
        // 创建通知渠道NotificationChannel,一个应用如果有多个notification,设置相同的channel时,通过会折叠在一起,不会铺展开来。
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(getActivity().getPackageName(),"MyChannel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("my notificationChannel");
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(getActivity(),getActivity().getPackageName())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Deep Link")
                .setContentText("点击我试试...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getPendingIntent())
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        notificationManagerCompat.notify(notificationId++,notification);
    }

    private PendingIntent getPendingIntent() {
        Bundle args = new Bundle();
        args.putString("user_name","jack");
        return Navigation.findNavController(getActivity(),R.id.button2)
                .createDeepLink()
                .setGraph(R.navigation.my_nav_graph)
                .setDestination(R.id.settingsFragment)
                .setArguments(args)
                .createPendingIntent();
    }
}