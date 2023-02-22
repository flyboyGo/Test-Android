package com.example.room.base.dataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.room.base.dao.StudentDao;
import com.example.room.base.enity.Student;

@Database(entities = {Student.class}, version = 1, exportSchema = true)
public  abstract class StudentDataBase extends RoomDatabase {

    private static StudentDataBase myInstance;
    private static final String DATABASE_NAME = "prestudent.db";

    public abstract StudentDao getStudentDao();

    public static  synchronized StudentDataBase getInstance(Context context){
        if(myInstance == null)
        {
            myInstance = Room.databaseBuilder(context.getApplicationContext(),
                    StudentDataBase.class,
                    DATABASE_NAME)
                    //.allowMainThreadQueries() // 允许在主线程中进行数据库操作,不推荐
                    .addMigrations(MIGRATION_1_2,MIGRATION_2_3,MIGRATION_3_4) // 更新数据库(注意实体类也需要更新)
                    .fallbackToDestructiveMigration()
                    .createFromAsset("preStudent.db")
                    .build();
        }

        return myInstance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE student ADD COLUMN sex INTEGER NOT NULL DEFAULT 1");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE student ADD COLUMN birthday TEXT");
        }
    };

    // 销毁重建策略
    // 注意 INTEGER类型默认创建时时NOT NULL,TEXT类型默认是不需要NOT NULL的
    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL ("CREATE TABLE temp_student (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "name TEXT,"+
                    "age INTEGER NOT NULL,"+
                    "sex TEXT DEFAULT 'M',"+
                    "bar_data INTEGER NOT NULL DEFAULT 1)");

            database.execSQL("INSERT INTO temp_student (name,age,sex)" +
                    "SELECT name,age,sex FROM student");
            database.execSQL("DROP TABLE student");
            database.execSQL("ALTER TABLE temp_student RENAME TO student");

        }
    };


}
