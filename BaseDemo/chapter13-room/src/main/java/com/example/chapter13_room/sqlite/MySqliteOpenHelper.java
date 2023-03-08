package com.example.chapter13_room.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 *  MySqliteOpenHelper 作为工具类 单例模式(1、构造函数私有化  2、对外提供实例函数)
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {

    // 数据库帮助类实例
    private static SQLiteOpenHelper myInstance;

    // 2、对外提供实例函数
    public static synchronized SQLiteOpenHelper getMyInstance(Context context){
        if(myInstance == null)
        {
            myInstance = new MySqliteOpenHelper(context, "MyDB.db",null, 1); // 以后数据库升级 将version 修改为2, 依次类推
        }
        return myInstance;
    }

    // 1、构造函数私有化
    private MySqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 数据库初始化
    // 创建表(表数据初始化  数据库第一次创建时调用; 第二次发现数据库文件已存在,就不会重复创建,意味着，此函只会调用一次)
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表: person表
        // 主键: _id 必须唯一   _id为标准写法 只能是Integer类型的
        String sql = "CREATE TABLE persons (\n" +
                         "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                         "name VARCHAR(50),\n" +
                         "age INTEGER\n" +
                         ")";

        db.execSQL(sql);
    }

    // 数据库升级
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
