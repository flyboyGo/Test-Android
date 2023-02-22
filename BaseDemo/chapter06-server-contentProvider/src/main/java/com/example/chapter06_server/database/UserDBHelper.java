package com.example.chapter06_server.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserDBHelper extends SQLiteOpenHelper {

    // 数据库名
    private static final String DB_NAME = "user.db";
    // 表名
    public static final String TABLE_NAME = "user_info";

    // 版本号,版本号发生改变时，会自动执行onUpgrade函数
    private static final int DB_VERSION = 1;

    // 数据库帮助器
    private static UserDBHelper mHelper = null;

    private UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static UserDBHelper getInstance(Context context)
    {
        if(mHelper == null)
            mHelper = new UserDBHelper(context);

        return mHelper;
    }



    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 拼接的sql语句
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " age INTEGER NOT NULL," +
                " height FLOAT NOT NULL," +
                " weight FLOAT NOT NULL," +
                " married INTEGER NOT NULL);";

        // 指定拼接的sql语句,execSQL无返回值
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
