package com.example.chapter13_room.room.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.chapter13_room.room.dao.StudentDao;
import com.example.chapter13_room.room.dataBase.StudentDataBase;
import com.example.chapter13_room.room.enity.Student;

import java.util.List;

// DB的引擎
public class DBEngine {

    private StudentDao studentDao;

    public DBEngine(Context context)
    {
        StudentDataBase studentDataBase = StudentDataBase.getInstance(context);
        this.studentDao = studentDataBase.getStudentDao();
    }

    // dao的 增删改查

    // insert插入
    public void insertStudents(Student ... students)
    {
        new InsertAsync(studentDao).execute(students);
    }

    // update 更新
    public void updateStudents(Student ... students)
    {
        new UpdateAsync(studentDao).execute(students);
    }

    // delete 删除 条件
    public void deleteStudents(Student ... students)
    {
        new DeleteAsync((studentDao)).execute(students);
    }

    // delete 删除 所有
    public void deleteAllStudents()
    {
        new DeleteAllAsync((studentDao)).execute();
    }

    // query 查询 所有
    public void queryAllStudents()
    {
        new QueryAsync(studentDao).execute();
    }



    // 如果我们想要数据库 默认是异步线程  ===== 异步操作

    // insert 插入
    static class InsertAsync extends AsyncTask<Student,Void,Void>
    {
        private StudentDao studentDao;

        public InsertAsync(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insertStudents(students);
            return null;
        }
    }

    // delete 删除
    static class DeleteAsync extends AsyncTask<Student,Void,Void>
    {
        private StudentDao studentDao;

        public DeleteAsync(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) { // 既然传递了student 进来，就是有条件删除
            studentDao.deleteStudent(students);
            return null;
        }
    }

    // delete 删除 所有
    static class DeleteAllAsync extends AsyncTask<Void,Void,Void>
    {
        private StudentDao studentDao;

        public DeleteAllAsync(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.deleteAllStudents();
            return null;
        }
    }

    // update 更新
    static class UpdateAsync extends AsyncTask<Student,Void,Void>
    {
        private StudentDao studentDao;

        public UpdateAsync(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.updateStudents(students);
            return null;
        }
    }

    // query 查询 所有
    static class QueryAsync extends AsyncTask<Void,Void,Void>
    {
        private StudentDao studentDao;

        public QueryAsync(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Student> studentList = studentDao.queryAllStudents();

            // 遍历查询的结果
            for (Student student : studentList) {
                Log.d("test", "doInBackground : 查询的每一项为 : " + student.toString());
            }
            return null;
        }
    }

}
