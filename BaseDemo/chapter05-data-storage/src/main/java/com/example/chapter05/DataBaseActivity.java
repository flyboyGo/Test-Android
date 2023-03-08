package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DataBaseActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_database;
    private String mDataBaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        tv_database= findViewById(R.id.tv_database);
        findViewById(R.id.btn_database_create).setOnClickListener(this);
        findViewById(R.id.btn_database_delete).setOnClickListener(this);

        // 生成一个测试数据的完整路径
        mDataBaseName = getFilesDir() + "/test.db";
    }

    @Override
    public void onClick(View v) {
        String desc = null;
                switch (v.getId())
        {
            case R.id.btn_database_create:
                // 创建或打开数据库。数据库如果不存在就创建它;如果数据库存在就打开它
                SQLiteDatabase db = openOrCreateDatabase(mDataBaseName, Context.MODE_PRIVATE, null);
                desc = String.format("数据库%s创建了%s",db.getPath(), (db != null) ? "成功":"失败");
                tv_database.setText(desc);
                break;
            case R.id.btn_database_delete:
                // 删除数据库
                boolean result = deleteDatabase(mDataBaseName);
                desc = String.format("数据库%s删除%s", mDataBaseName, result ? "成功":"失败");
                tv_database.setText(desc);
                break;
        }
    }
}