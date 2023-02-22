package com.example.rxjava;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.List;
import java.util.PrimitiveIterator;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MapDemo {

//    @Test
//    public void testCommonNetwork()
//    {
//        StudentModel.init();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Student> studentList = StudentModel.getStudentList();
//                for (Student student : studentList) {
//                    List<Student.Course> courseList = student.getCourseList();
//                    for (Student.Course course : courseList) {
//                        System.out.println(student.getName() + " : " + course);
//                    }
//                }
//            }
//        }).start();
//    }

    @Test
    public void test(){
        StudentModel.init();
        testMapNetwork();
        testFlatMapNetwork();
    }

    private void testMapNetwork(){
        Observable.fromIterable(StudentModel.getStudentList())
                .map(new Function<Student, List<Student.Course>>() {
                    @Override
                    public List<Student.Course> apply(@NonNull Student student) throws Exception {

                        return student.courseList;
                    }
                }).subscribe(new Consumer<List<Student.Course>>() {
            @Override
            public void accept(List<Student.Course> courseList) throws Exception {
                for (Student.Course course : courseList) {
                    System.out.println(course.toString());
                }
            }
        });
    }

    private void testFlatMapNetwork(){
        Observable.fromIterable(StudentModel.getStudentList())
        .flatMap(new Function<Student, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Student student) throws Exception {
                return Observable.fromIterable(student.getCourseList());
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(o);
            }
        });
    }
}
