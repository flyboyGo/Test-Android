package com.example.chapter06_client;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class MonitorSmsActivity extends AppCompatActivity {

    private SmsGetObserver smsGetObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_sms);

        //Uri.parse("content://sms/raw");
        //Uri.parse("content://sms/inbox");
        //Android5.0之后似乎无法单独观察某个信箱，只能监控整个短信

        // 给指定的Uri注册内容观察器，一旦数据发生变化,就触发观察期的onChange方法
        Uri uri = Uri.parse("content://sms");

        // notifyForDescendents：
        // false ：表示精确匹配，即只匹配该Uri，true ：表示可以同时匹配其派生的Uri
        // 假设UriMatcher 里注册的Uri共有一下类型：
        // 1.content://AUTHORITIES/table
        // 2.content://AUTHORITIES/table/#
        // 3.content://AUTHORITIES/table/subtable
        // 假设我们当前需要观察的Uri为content://AUTHORITIES/student:
        // 如果发生数据变化的 Uri 为 3。
        // 当notifyForDescendents为false，那么该ContentObserver会监听不到，但是当notifyForDescendents 为ture，能捕捉该Uri的数据库变化。

        smsGetObserver = new SmsGetObserver(this);
        getContentResolver().registerContentObserver(uri,true, smsGetObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消注册
        getContentResolver().unregisterContentObserver(smsGetObserver);
    }

    /*
      内容观察器ContentObserver给目标内容注册一个观察器，目标内容的数据一旦发生变化，观察器规定好的动作马上触发，从而执行开发者预先定义的代码。
     */

    private static class SmsGetObserver extends ContentObserver{

        private Context context;

        public SmsGetObserver(Context context) {
            // 声明一个匿名处理器对象
            super(new Handler(Looper.getMainLooper()));
            this.context = context;
        }

        @SuppressLint("Range")
        @Override
        public void onChange(boolean selfChange, @Nullable Uri uri) {
            super.onChange(selfChange, uri);
            // onChange会多次调用，收到一条短信会调用两次onChange
            // mUri===content://sms/raw/20
            // mUri===content://sms/inbox/20

            // 安卓7.0以上系统，点击标记为已读，也会调用一次
            // mUri===content://sms

            // 收到一条短信都是uri后面都会有确定的一个数字，对应数据库的_id，比如上面的20
            // content://sms/2(我们需要的)
            if(uri == null)
            {
                return;
            }
            if(uri.toString().contains("content://sms/raw") || uri.toString().equals("content://sms"))
            {
                return;
            }
            Log.d("test",uri.toString());

            // 通过内容解析器获取符合条件的结果游标集合
            Cursor cursor = context.getContentResolver().query(uri, new String[]{"address", "body", "date"}, null, null, "date DESC");
            if(cursor.moveToNext())
            {
                // 短信的发送号码
                String address = cursor.getString(cursor.getColumnIndex("address"));
                // 短信内容
                String content = cursor.getString(cursor.getColumnIndex("body"));
                Log.d("test",String.format("sender:%s,content:%s",address,content));
            }
        }
    }
}