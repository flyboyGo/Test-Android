package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.KeyEventDispatcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActStartActivity extends AppCompatActivity {

    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_start);

        btn = findViewById(R.id.btn_act_next);
        btn.setOnClickListener(new MyOnClickListener(btn, this));
        Intent intent = new Intent(ActStartActivity.this,ActFinishActivity.class);

        Log.d("Test", "ActStartActivity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Test", "ActStartActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Test", "ActStartActivity onResume");
    }

    /*
       onPause： Activity是去焦点，但仍然可见；
       onStop：Activity在后台不可见时触发（完全被另一个Activity挡住，或者程序在后台运行）
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Test", "ActStartActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Test", "ActStartActivity onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Test", "ActStartActivity onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Test", "ActStartActivity onDestroy");
    }

    static class MyOnClickListener implements View.OnClickListener{
        private Button button;
        private Context context;
        public MyOnClickListener(Button btn, Context context)
        {
            this.button = btn;
            this.context = context;
        }
        @Override
        public void onClick(View view) {

            // 显示跳转
            // 1、在Intent的构造函数中指定
            // Intent intent = new Intent(context,ActFinishActivity.class);

            // 2、调用意图对象的setClass方法指定
            // intent.setClass(context,ActFinishActivity.class);

            // 3、调用意图对象的setComponent方法指定
            // 方式一:
            // ComponentName component = new ComponentName(context, ActFinishActivity.class);
            // 方式二:指定包名、类名
            ComponentName component = new ComponentName("com.example.chapter03", "com.example.chapter03.ActFinishActivity");
            Intent intent = new Intent();
            intent.setComponent(component);

            // 开始跳转页面
            context.startActivity(intent);
        }
    }
}