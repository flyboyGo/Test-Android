package com.example.chapter05;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.chapter05.database.BookDataBase;
import com.example.chapter05.enity.Book;

import java.util.HashMap;

public class MyApplication extends Application {

    /*
         适合在Application中保存的全局变量主要有下面3类数据：
         1、会频繁读取的信息，如用户名、手机号等。
         2、不方便由意图传递的数据，例如位图对象、非字符串类型的集合对象等。
         3、容易因频繁分配内存而导致内存泄漏的对象，如Handler对象等。
     */

    // 声明一个公共的信息映射对象，可当作全局变量使用
    public HashMap<String,String> infoMap = new HashMap<>();

    // 声明一个书籍数据库对象
    private BookDataBase bookDataBase;

    // 单例模式
    private  static  MyApplication myApplication;

    public static MyApplication getInstance(){
        return myApplication;
    }


    // 在App启动前调用
    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
        Log.d("test","onCreate");

        // 构建书籍数据库的实例
        bookDataBase = Room.databaseBuilder(this,BookDataBase.class,"book")
                // 允许迁移数据库（发生数据库变更时，Room默认删除原数据库再创建新数据库。如此一来原来的记录会丢失，故而要改为迁移方式以便保存原有记录）
                .addMigrations()
                // 允许在主线程中操作数据库（Room默认不能在主线程中操作数据库）
                .allowMainThreadQueries()
                .build();
    }

    // 在App重止时调用
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("test","onTerminate");
    }

    // 在配置改变时调用，例如横屏变为竖屏
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("test","onConfigurationChanged");
    }

    // 获取书籍数据库的实例
    public BookDataBase getBookDataBase()
    {
        return bookDataBase;
    }
}
