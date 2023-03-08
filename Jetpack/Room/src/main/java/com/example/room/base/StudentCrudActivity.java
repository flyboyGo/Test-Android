package com.example.room.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.room.R;
import com.example.room.base.adapter.StudentRecycleViewAdapter;
import com.example.room.base.dao.StudentDao;
import com.example.room.base.dataBase.StudentDataBase;
import com.example.room.base.enity.Student;
import com.example.room.base.manager.DBEngine;

import java.util.ArrayList;
import java.util.List;

public class StudentCrudActivity extends AppCompatActivity {

    private List<Student> studentList;
    private StudentRecycleViewAdapter adapter;
    private DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_student);

        RecyclerView recycleView = findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        studentList = new ArrayList<>();
        adapter = new StudentRecycleViewAdapter(studentList,this);
        recycleView.setAdapter(adapter);

        dbEngine = new DBEngine(this,adapter);
    }

    public void myInsert(View view)
    {
        Student student = new Student("Rose",16);
        Student student2 = new Student("Jack",22);
        dbEngine.insertStudents(student,student2);
    }

    public void myQuery(View view)
    {
        dbEngine.queryAllStudents();
    }

    public void myDelete(View view) {
        Student student = new Student(10);
        dbEngine.deleteStudents(student);
    }

    public void myUpdate(View view) {
        Student student = new Student(10,"Jason",26);
        dbEngine.updateStudents(student);
    }
}