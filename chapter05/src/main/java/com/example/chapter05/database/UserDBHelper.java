package com.example.chapter05.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chapter05.enity.User;

import java.util.ArrayList;
import java.util.List;


public class UserDBHelper extends SQLiteOpenHelper {

    // 数据库名
    private static final String DB_NAME = "user.db";
    // 表名
    private static final String TABLE_NAME = "user_info";
    // 版本号
    private static final int DB_VERSION = 1;
    // 数据库帮助器
    private static UserDBHelper mHelper = null;

    //数据库连接
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

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

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink()
    {
        if(mRDB == null || !mRDB.isOpen())
        {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink()
    {
        if(mWDB == null || !mWDB.isOpen())
        {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库
    public void closeLink()
    {
        if(mRDB != null && mRDB.isOpen())
        {
            mRDB.close();
            mRDB = null;
        }
        if(mWDB != null && mWDB.isOpen())
        {
            mWDB.close();
            mWDB = null;
        }
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

        // 指定拼接的sql语句
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 添加函数
    public long insert(User user)
    {
        ContentValues values = new ContentValues();
        values.put("name",user.name);
        values.put("age",user.age);
        values.put("height",user.height);
        values.put("weight",user.weight);
        values.put("married",user.married);
        // 执行插入记录动作，该语句返回插入记录的行号
        // 如果第三个参数values 为Null或者元素个数为0， 由于insert()方法要求必须添加一条除了主键之外其它字段为Null值的记录，
        // 为了满足SQL语法的需要， insert语句必须给定一个字段名 ，如：insert into person(name) values(NULL)，
        // 倘若不给定字段名 ， insert语句就成了这样： insert into person() values()，显然这不满足标准SQL的语法。
        // 如果第三个参数values 不为Null并且元素的个数大于0 ，可以把第二个参数设置为null 。
        return mWDB.insert(TABLE_NAME,null,values);
    }

    // 删除函数
    public long deleteByName(String name)
    {
        // 删除所有
        // return mWDB.delete(TABLE_NAME,"1=1",null);
        return mWDB.delete(TABLE_NAME,"name = ?", new String[]{name});
    }

    // 修改函数
    public long update(User user)
    {
        ContentValues values = new ContentValues();
        values.put("name",user.name);
        values.put("age",user.age);
        values.put("height",user.height);
        values.put("weight",user.weight);
        values.put("married",user.married);
        return mWDB.update(TABLE_NAME,values,"name = ?",new String[]{user.name});
    }

    // 查询全部函数
    public List<User> queryByName(String name)
    {
        List<User> list = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果的游标
        Cursor  cursor;
        if(name == null)
        {
            cursor = mRDB.query(TABLE_NAME, null,null, null, null, null, null);
        }
        else
        {
            cursor = mRDB.query(TABLE_NAME, null,"name = ?", new String[]{name}, null, null, null);
        }

        while(cursor.moveToNext())
        {
            User user = new User();
            user.id = cursor.getInt(0);
            user.name = cursor.getString(1);
            user.age = cursor.getInt(2);
            user.height = cursor.getFloat(3);
            user.weight = cursor.getFloat(4);
            user.married = cursor.getInt(4) == 1 ? true : false;
            list.add(user);
        }
        return list;
    }


}
