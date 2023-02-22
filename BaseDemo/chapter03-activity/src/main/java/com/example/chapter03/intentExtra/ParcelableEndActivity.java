package com.example.chapter03.intentExtra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.chapter03.R;

public class ParcelableEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable_end);

        Intent intent = getIntent();
        // 获取传递过来的实现了Parcelable接口的实例对象
        Student student = intent.getParcelableExtra("student");
        Log.d("test", student.toString());
    }
}