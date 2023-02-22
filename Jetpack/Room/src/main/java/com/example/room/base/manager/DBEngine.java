package com.example.room.base.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.example.room.base.adapter.StudentRecycleViewAdapter;
import com.example.room.base.dao.StudentDao;
import com.example.room.base.dataBase.StudentDataBase;
import com.example.room.base.enity.Student;

import java.util.List;

public class DBEngine {

    private StudentDao studentDao;
    public  StudentRecycleViewAdapter adapter;

    public DBEngine(Context context,StudentRecycleViewAdapter adapter) {
        StudentDataBase dataBase = StudentDataBase.getInstance(context);
        this.studentDao = dataBase.getStudentDao();
        this.adapter = adapter;
    }


    // insert插入
    public void insertStudents(Student ... students)
    {
        new InsertAsync(studentDao).execute(students);
    }

    // query 查询 所有
    public void queryAllStudents()
    {
        new QueryAllAsync(studentDao).execute();
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

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            adapter.notifyDataSetChanged();
        }
    }

    // query 查询所有
    class QueryAllAsync extends AsyncTask<Void,Void,Void>
    {
        private StudentDao studentDao;

        public QueryAllAsync(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Student> studentList = studentDao.getAllStudents();
            adapter.setStudentList(studentList);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            adapter.notifyDataSetChanged();
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

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            adapter.notifyDataSetChanged();
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

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            adapter.notifyDataSetChanged();
        }
    }
}
