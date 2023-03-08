package com.example.chapter18_handler_thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandlerThreadActivity extends AppCompatActivity {

    // 图片地址集合
    private String url[]={
            "https://img-blog.csdn.net/20160903083245762",
            "https://img-blog.csdn.net/20160903083252184",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083311972",
            "https://img-blog.csdn.net/20160903083319668",
            "https://img-blog.csdn.net/20160903083326871"
    };
    private ImageView imageView;
    private Handler mUIHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.d("test","次数: " + msg.what);
            ImageModel model = (ImageModel)msg.obj;
            imageView.setImageBitmap(model.bitmap);
        }
    };
    private HandlerThread handlerThread;
    private Handler childHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        imageView = this.findViewById(R.id.imageView);
        // 创建异步的HandlerThread
        handlerThread = new HandlerThread("downloadImage");
        // 必须开始线程
        handlerThread.start();
        // 子线程Handler
        childHandler = new Handler(handlerThread.getLooper(),new ChildCallback());
        for(int i = 0; i < 7; i++){
            // 每一个秒更新图片
            childHandler.sendEmptyMessageDelayed(i,1000 * i);
        }
    }

    // 该callback运行于子线程
    class ChildCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            // 在子线程中进行网络请求
            Bitmap bitmap = downloadUrlBitmap(url[msg.what]);
            ImageModel imageModel = new ImageModel();
            imageModel.bitmap = bitmap;
            imageModel.uri = url[msg.what];
            Message message = Message.obtain();
            message.what = msg.what;
            message.obj = imageModel;
            // 通知主线程更新UI
            mUIHandler.sendMessage(message);
            return false;
        }
    }

    private Bitmap downloadUrlBitmap(String urlString) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        Bitmap bitmap=null;
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

    class ImageModel{
        public Bitmap bitmap;
        public String uri;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quitSafely();
    }
}