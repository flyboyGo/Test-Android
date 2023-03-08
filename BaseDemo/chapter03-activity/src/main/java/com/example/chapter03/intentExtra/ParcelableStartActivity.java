package com.example.chapter03.intentExtra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chapter03.R;

public class ParcelableStartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable_start);

        findViewById(R.id.btn_jump_parcelable).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(ParcelableStartActivity.this,ParcelableEndActivity.class);
        // 传递 实现了Parcelable接口的实例对象
        Student student = new Student("rose", 16);
        intent.putExtra("student",student);
        startActivity(intent);
    }
}