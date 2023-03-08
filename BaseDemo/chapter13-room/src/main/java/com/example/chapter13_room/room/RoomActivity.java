package com.example.chapter13_room.room;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.chapter13_room.R;
import com.example.chapter13_room.room.enity.Student;
import com.example.chapter13_room.room.manager.DBEngine;

import java.util.PrimitiveIterator;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    private DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        dbEngine = new DBEngine(this);

        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_delete_all).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query_all).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_insert: // 插入
                Student student = new Student("flyboy", 23);
                Student student2 = new Student("李鹏飞", 18);
                Student student3 = new Student("jack", 16);
                dbEngine.insertStudents(student, student2, student3);
                break;
            case R.id.btn_delete:
                Student student4 = new Student();
                student4.setId(3); // 删除 下标为 3 的记录
                dbEngine.deleteStudents(student4);
                break;
            case R.id.btn_delete_all: // 删除 全部
                dbEngine.deleteAllStudents();
                break;
            case R.id.btn_update: // 更新 下标为 3 的记录
                Student student5 = new Student("rose", 14);
                student5.setId(3);
                dbEngine.updateStudents(student5);
                break;
            case R.id.btn_query_all: // 查询 全部
                dbEngine.queryAllStudents();
                break;
            default:
                break;
        }
    }
}