package com.example.chapter13_room.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.chapter13_room.R;

public class SQLiteActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        findViewById(R.id.create_db).setOnClickListener(this);
        findViewById(R.id.insert_db).setOnClickListener(this);
        findViewById(R.id.query_db).setOnClickListener(this);
        findViewById(R.id.update_db).setOnClickListener(this);
        findViewById(R.id.delete_db).setOnClickListener(this);
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View v) {
        // 获取数据库帮助器
        SQLiteOpenHelper openHelper = MySqliteOpenHelper.getMyInstance(this);
        SQLiteDatabase database = null;
        switch (v.getId()){
            case R.id.create_db:
                // database 文件 的创建,需要直接获取读、写数据库
                openHelper.getWritableDatabase();
                break;

            case R.id.query_db:
                // 获取读数据库
                database = openHelper.getReadableDatabase();
                // 数据库打开成功,返回true
                if(database.isOpen())
                {
                    String sql = "SELECT * FROM persons";
                    // 查询,返回游标
                    Cursor cursor = database.rawQuery(sql, null);
                    // 迭代游标
                    while(cursor.moveToNext())
                    {
                        int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int age = cursor.getInt(cursor.getColumnIndex("age"));
                        Log.d("test","_id : " + _id + " name " + name + " age " + age);
                    }

                    // 关闭游标
                    cursor.close();

                    // 关闭数据库
                    database.close();
                }
                break;
            case R.id.insert_db:
                // 获取写数据库
                database = openHelper.getWritableDatabase();
                // 判断数据库是否打开
                if(database.isOpen())
                {
                    String sql = "INSERT INTO persons (name,age) VALUES('flyboy',23)";

                    database.execSQL(sql);

                    // 关闭数据库
                    database.close();
                }
                break;
            case R.id.update_db:
                // 获取写数据库
                database = openHelper.getWritableDatabase();
                // 判断数据库是否打开
                if(database.isOpen())
                {
                    String sql = "UPDATE persons set name = ?, age = ? WHERE _id = ?";

                    database.execSQL(sql,new Object[]{"李鹏飞", 18, 5});

                    // 关闭数据库
                    database.close();
                }
                break;
            case R.id.delete_db:
                // 获取写数据库
                database = openHelper.getWritableDatabase();
                // 判断数据库是否打开
                if(database.isOpen())
                {
                    String sql = "DELETE FROM persons WHERE _id = ?";

                    database.execSQL(sql,new Object[]{2});

                    // 关闭数据库
                    database.close();
                }
                break;
            default:
                break;
        }
    }
}