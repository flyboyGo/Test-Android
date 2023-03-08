package com.example.chapter10_service_extra.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExtendIntentService extends IntentService {

    public static final String DOWNLOAD_URL="download_url";
    public static final String INDEX_FLAG="index_flag";
    public static UpdateUI updateUI;

    public ExtendIntentService() {
        super("ExtendIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test","ExtendIntentService onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d("test","ExtendIntentService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("test","ExtendIntentService onStart");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 可以执行耗时任务(发起网络请求)
        // Intent是从Activity发过来的,携带识别参数,根据参数的不同执行不同的任务
        Bitmap bitmap=downloadUrlBitmap(intent.getStringExtra(DOWNLOAD_URL));
        Message message = Message.obtain();
        message.what = intent.getIntExtra(INDEX_FLAG,0);
        message.obj = bitmap;
        // 通过主线程更新UI
        if(updateUI != null){
            updateUI.updateUI(message);
        }
        Log.d("test","ExtendIntentService onHandleIntent");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("test","ExtendIntentService onBind");
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("test","ExtendIntentService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test","ExtendIntentService onDestroy");
    }

    public interface UpdateUI{
        void updateUI(Message message);
    }

    public static void setUpdateUI(UpdateUI updateUIInterface){
        updateUI = updateUIInterface;
    }


    private Bitmap downloadUrlBitmap(String urlString) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        Bitmap bitmap = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            bitmap= BitmapFactory.decodeStream(in);
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
