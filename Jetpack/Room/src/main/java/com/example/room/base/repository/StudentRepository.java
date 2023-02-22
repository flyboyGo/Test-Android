package com.example.room.base.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.room.base.adapter.StudentRecycleViewAdapter;
import com.example.room.base.dao.StudentDao;
import com.example.room.base.dataBase.StudentDataBase;
import com.example.room.base.enity.Student;

import java.util.List;

public class StudentRepository {

    private StudentDao studentDao;

    public StudentRepository(Context context) {
        StudentDataBase dataBase = StudentDataBase.getInstance(context);
        this.studentDao = dataBase.getStudentDao();
    }

    // query 查询所有
    public LiveData<List<Student>> queryAllStudentsLive()
    {
        return studentDao.getAllStudentsLive();
    }

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

    // delete 条件删除
    public void deleteStudents(Student ... students)
    {
        new DeleteAsync((studentDao)).execute(students);
    }

    // delete 清空
    public void deleteAllStudents()
    {
        new DeleteAllAsync((studentDao)).execute();
    }


    // 如果我们想要数据库 默认是异步线程  ===== 异步操作

    // insert 插入
    class InsertAsync extends AsyncTask<Student,Void,Void>
    {
        private StudentDao studentDao;

        public InsertAsync(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insertStudent(students);
            return null;
        }
    }

    // delete 条件删除
    class DeleteAsync extends AsyncTask<Student,Void,Void>
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

    // delete清空
    class DeleteAllAsync extends AsyncTask<Void,Void,Void>
    {

        private StudentDao studentDao;

        public DeleteAllAsync(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.deleteAllStudent();
            return null;
        }
    }

    // update 更新
    class UpdateAsync extends AsyncTask<Student,Void,Void>
    {
        private StudentDao studentDao;

        public UpdateAsync(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.updateStudent(students);
            return null;
        }
    }

}
