package com.example.chapter03.intentExtra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.chapter03.R;


public class SerializableEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable_end);

        Intent intent = getIntent();
        // 获取传递过来的序列化的对象
        User user = (User)intent.getSerializableExtra("user");
        Log.d("test","接收到的序列化对象 : " + user.toString());
    }
}