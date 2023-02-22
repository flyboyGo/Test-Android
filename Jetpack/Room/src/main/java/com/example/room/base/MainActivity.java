package com.example.room.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.room.R;
import com.example.room.base.adapter.StudentRecycleViewAdapter;
import com.example.room.base.enity.Student;
import com.example.room.base.repository.StudentRepository;
import com.example.room.base.viewmodel.StudentViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Student> studentList;
    private StudentRecycleViewAdapter adapter;
    private StudentViewModel studentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycleView = findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        studentList = new ArrayList<>();
        adapter = new StudentRecycleViewAdapter(studentList,this);
        recycleView.setAdapter(adapter);

        studentViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(StudentViewModel.class);
        studentViewModel.getAllStudentsLive().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                adapter.setStudentList(students);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void myInsert(View view)
    {
        Student student = new Student("Rose",16);
        Student student2 = new Student("Jack",22);
        studentViewModel.myInsert(student,student2);
    }

    public void myDelete(View view) {
        Student student = new Student(30);
        studentViewModel.myDelete(student);
    }

    public void myUpdate(View view) {
        Student student = new Student(28,"Jason",26);
        studentViewModel.myUpdate(student);
    }

    public void myClear(View view) {
        studentViewModel.myClear();
    }
}