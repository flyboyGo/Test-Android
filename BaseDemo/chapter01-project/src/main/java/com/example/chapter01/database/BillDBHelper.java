package com.example.chapter01.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.chapter01.enity.BillInfo;

import java.util.ArrayList;
import java.util.List;


public class BillDBHelper extends SQLiteOpenHelper {
    // 数据库名
    private static final String DB_NAME = "bill.db";
    // 表名
    // 账单信息表
    private static final String TABLE_BILLS_INFO = "bill_info";

    // 版本号,版本号发生改变时，会自动执行onUpgrade函数
    private static final int DB_VERSION = 1;

    // 数据库帮助器
    private static BillDBHelper mHelper = null;

    //数据库连接
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private BillDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private BillDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static BillDBHelper getInstance(Context context)
    {
        if(mHelper == null)
            mHelper = new BillDBHelper(context);

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
        // 创建账单信息表
        String sql = null;
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BILLS_INFO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " date VARCHAR NOT NULL," +
                " type INTEGER NOT NULL," +
                " amount DOUBLE NOT NULL," +
                " remark VARCHAR NOT NULL);";
        db.execSQL(sql);
    }

    // 版本更新函数
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 保存一条账单记录
    public long save(BillInfo bill)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("date",bill.date);
        contentValues.put("type",bill.type);
        contentValues.put("amount",bill.amount);
        contentValues.put("remark",bill.remark);
        return mWDB.insert(TABLE_BILLS_INFO,null,contentValues);
    }

    // 查询账单信息
    @SuppressLint("Range")
    public List<BillInfo> queryByMonth(String yearMonth)
    {
        List<BillInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_BILLS_INFO + " WHERE date LIKE '" + yearMonth + "%'";
        Log.d("test",sql);
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext())
        {
            BillInfo billInfo = new BillInfo();
            billInfo.id = cursor.getInt(cursor.getColumnIndex("_id"));
            billInfo.date = cursor.getString(cursor.getColumnIndex("date"));
            billInfo.remark = cursor.getString(cursor.getColumnIndex("remark"));
            billInfo.type = cursor.getInt(cursor.getColumnIndex("type"));
            billInfo.amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            list.add(billInfo);
        }
        return list;
    }
}

