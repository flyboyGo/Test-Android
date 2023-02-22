package com.example.chapter03.intentExtra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chapter03.R;


public class SerializableStartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable_start);

        findViewById(R.id.btn_jump_serializable).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SerializableStartActivity.this,SerializableEndActivity.class);

        // 传递实现了序列化的对象
        User user = new User(1,"tony",28);
        intent.putExtra("user",user);
        startActivity(intent);
    }

}