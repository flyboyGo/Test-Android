package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AppWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_write);

        application = MyApplication.getInstance();

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);

        findViewById(R.id.btn_save).setOnClickListener(this);

        reload();
    }

    private void reload() {
        String name = application.infoMap.get("name");
        if(name == null)
        {
            return;
        }
        String age = application.infoMap.get("age");
        String height = application.infoMap.get("height");
        String weight = application.infoMap.get("weight");
        String married = application.infoMap.get("married");

        et_name.setText(name);
        et_age.setText(age);
        et_height.setText(height);
        et_weight.setText(weight);
        ck_married.setChecked(married.equals("是") ? true : false);
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();

        application.infoMap.put("name",name);
        application.infoMap.put("age",age);
        application.infoMap.put("height",height);
        application.infoMap.put("weight",weight);
        application.infoMap.put("married",ck_married.isChecked() ? "是" : "否");

        // 提交信息
        Toast.makeText(this,"信息已保存",Toast.LENGTH_SHORT).show();
    }
}