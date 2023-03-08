package com.example.chapter13_room.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chapter13_room.room.enity.Student;

import java.util.List;

@Dao // DataBase access object == 对数据库表 进行 增删改查
public interface StudentDao {

    // 增
    @Insert
    void insertStudents(Student ... students);

    // 改
    @Update
    void updateStudents(Student ... students);

    // 删 所有   @Delete 单个条件删除使用
    @Query("DELETE FROM Student")
    void deleteAllStudents();

    // 删除 条件
    @Delete
    void deleteStudent(Student ... students);

    // 查 所有
    @Query("SELECT * FROM Student ORDER BY ID DESC")
    List<Student> queryAllStudents();
}
