package com.example.rxjava;

import java.util.ArrayList;
import java.util.List;

public class StudentModel {

    static List<Student> studentList;

    public static void init(){
        studentList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Student student = new Student();
            List<Student.Course> courseList = new ArrayList<>();
            for(int j = 0; j < 4; j++){
                Student.Course course = new Student.Course("Course" + j);
                courseList.add(course);
            }
            student.setName("Student" + i);
            student.setCourseList(courseList);
            studentList.add(student);
        }
    }

    public static List<Student> getStudentList(){
        return studentList;
    }
}
