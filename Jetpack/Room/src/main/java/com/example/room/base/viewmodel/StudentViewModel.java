package com.example.room.base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.room.base.enity.Student;
import com.example.room.base.repository.StudentRepository;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {

    private LiveData<List<Student>> studentListLiveData;
    private StudentRepository studentRepository;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        this.studentRepository = new StudentRepository(application);
    }

    public LiveData<List<Student>> getAllStudentsLive()
    {
        myQuery();
        return studentListLiveData;
    }

    public void myQuery()
    {
        studentListLiveData = studentRepository.queryAllStudentsLive();
    }

    public void myInsert(Student ... students)
    {
        studentRepository.insertStudents(students);
    }

    public void myDelete(Student ... students) {
        studentRepository.deleteStudents(students);
    }

    public void myClear() {
        studentRepository.deleteAllStudents();
    }

    public void myUpdate(Student ... students) {
        studentRepository.updateStudents(students);
    }
}
