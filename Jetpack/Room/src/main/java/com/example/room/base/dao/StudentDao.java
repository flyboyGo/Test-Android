package com.example.room.base.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.room.base.enity.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student ... students);

    @Delete
    void deleteStudent(Student ... students);

    @Update
    void updateStudent(Student ... students);

    @Query("SELECT * FROM Student")
    List<Student> getAllStudents();

    @Query("SELECT * FROM Student")
    LiveData<List<Student>> getAllStudentsLive();

    @Query("DELETE FROM student")
    void deleteAllStudent();
}
