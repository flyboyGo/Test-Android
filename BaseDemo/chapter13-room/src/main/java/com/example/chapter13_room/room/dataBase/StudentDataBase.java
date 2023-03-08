package com.example.chapter13_room.room.dataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.chapter13_room.room.dao.StudentDao;
import com.example.chapter13_room.room.enity.Student;

// 数据库 关联 前面的 表 数据库信息
@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentDataBase extends RoomDatabase {

    // 用户只需要操作 DAO , 所以我们必须暴露 DAO,用户才能获取到
    public abstract StudentDao getStudentDao();

    // 单例模式 返回 DB
    private static  StudentDataBase myInstance;

    public static synchronized StudentDataBase getInstance(Context context)
    {
        if(myInstance == null)
        {
            myInstance = Room.databaseBuilder(context.getApplicationContext(),StudentDataBase.class, "student_database")
                        // 数据库 默认是异步线程
                        // 慎用: 强制开启 主线程 也可以操作 数据库 (测试可以用，真实环境下 不要用)
                        //.allowMainThreadQueries()
                      .build();
        }
        return myInstance;
    }
}
