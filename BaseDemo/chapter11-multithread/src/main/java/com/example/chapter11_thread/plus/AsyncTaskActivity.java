package com.example.chapter11_thread.plus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chapter11_thread.R;

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_show;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        findViewById(R.id.btn_async_task).setOnClickListener(this);
        findViewById(R.id.btn_download).setOnClickListener(this);
        progressBar = findViewById(R.id.pb_download);

        tv_show = findViewById(R.id.tv_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_async_task:
                new MyTask().execute();
                break;
            default:
                new DownTask().execute();
                break;
        }
    }


    class MyTask extends AsyncTask<Void,Void,String>{

        // 当前仍在主线程中,做一些准备工作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test","onPreExecute");
        }

        // 切换异步线程(子线程中)中执行(已从主线程切换到子线程中)
        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("test","doInBackground");

            // 通知主线程我当前的进度是多少(会触发onProgressUpdate)
            publishProgress();

            String msg = "Welcome Back";

            return msg;
        }

        // 当前切换到主线程,可以根据传递的参数作UI的更新(执行线程过程中不一定会被调用,依赖于publishProgress()方法的调用)
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            Log.d("test","onProgressUpdate");
        }

        // 切换到主线程中执行
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("test","onPostExecute");

            tv_show.setText(s);
        }
    }

    class DownTask extends AsyncTask<Void,Integer,Boolean>{

        private int progress = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test","准备下载!");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d("test","正在下载!");
                try {
                    while (true) {
                        Thread.sleep(1 * 1000);
                        progress+=10;
                        if(progress >= 100)
                        {
                            break;
                        }
                        // 通知主线程我的进度
                        publishProgress(progress);
                    }
                } catch (Exception e) {
                    return false;
                }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("test","下载进度 : " + values[0]);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean)
            {
                Log.d("test","下载成功!");
                progressBar.setVisibility(View.GONE);
            }
            else
            {
                Log.d("test","下载失败!");
            }
        }
    }
}