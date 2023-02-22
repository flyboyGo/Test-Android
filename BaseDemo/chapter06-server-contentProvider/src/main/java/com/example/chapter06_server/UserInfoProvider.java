package com.example.chapter06_server;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.chapter06_server.UserInfoContent;
import com.example.chapter06_server.database.UserDBHelper;

public class UserInfoProvider extends ContentProvider {

    // 数据库帮助器
    private UserDBHelper dbHelper;

    // Uri匹配器
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    // Uri请求路径分别对应的请求码
    private  static  final int USERS = 1;
    private  static  final int USER = 2;

    // 在Uri匹配器中添加指定的数据路径
    static
    {
        URI_MATCHER.addURI( UserInfoContent.AUTHORITIES,"/user", USERS);
        URI_MATCHER.addURI( UserInfoContent.AUTHORITIES,"/user/#", USER);
    }

    public UserInfoProvider() {
    }

    @Override
    public boolean onCreate() {

        Log.d("test","UserInfoProvider onCreate");
        // 实例化数据库帮助器实例
        dbHelper = UserDBHelper.getInstance(getContext());

        // 实例化监听器
        UserInfoObserver userInfoObserver = new UserInfoObserver(getContext());
        // 注册监听器
        getContext().getContentResolver().registerContentObserver(UserInfoContent.CONTENT_URI,true,userInfoObserver);

        // 返回true,ContentProvider实例化成功
        return true;
    }

    // 观察者类ContentObserver
    private static class UserInfoObserver extends ContentObserver {

        private Context context;

        public UserInfoObserver(Context context)
        {
            super(new Handler(Looper.getMainLooper()));
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange, @Nullable Uri uri) {
            super.onChange(selfChange, uri);
            Log.d("test",uri.toString());
        }
    }

    /*
       Uri(通用资源标识符 Universal Resource Identifier)，代表数据操作的地址，
       每一个 ContentProvider 都会有唯一的地址。ContentProvider 使用的Uri 语法结构如下：

       content://authority/data_path/id
      「content:// 」 是通用前缀，表示该Uri用于ContentProvider定位资源。
      「authority 」 是授权者名称(全类名)，用来确定具体由哪一个ContentProvider提供资源。因此一般authority都由类的小写全称组成，以保证唯一性。
      「data_path 」 是数据路径，用来确定请求的是哪个数据集。(通常是表名)
      「id 」 是数据编号，用来请求单条数据。如果是多条,这个字段忽略

       content://com.example.chapter06_server.UserInfoProvider/user
     */

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.d("test","UserInfoProvider insert");
        if(URI_MATCHER.match(uri) == USERS)
        {
            SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
            long row_id = writableDatabase.insert(UserDBHelper.TABLE_NAME, null, values);
            // 判断插入是否成功
            if(row_id > 0)
            {
                // 如果添加成功，利用新记录的行号生成新的地址
                Uri newUri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, row_id);
                // 通知监听器，数据发生改变
                getContext().getContentResolver().notifyChange(newUri,null);
            }
        }

        // 返回,Uri(通用资源标识符 Universal Resource Identifier)，代表数据操作的地址，
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.d("test","UserInfoProvider query");
        if(URI_MATCHER.match(uri) == USERS)
        {
            SQLiteDatabase readableDatabase = dbHelper.getReadableDatabase();
            // 返回查询的结果，游标
            return readableDatabase.query(UserDBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        }
        return  null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d("test","uri = " + uri.toString());
        int count = 0;
        switch (URI_MATCHER.match(uri))
        {
            // content://com.example.chapter06_server.UserInfoProvider/user
            // 删除多行
            case USERS:
                SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
                count = writableDatabase.delete(UserDBHelper.TABLE_NAME, selection, selectionArgs);
                writableDatabase.close();
                break;

            // content://com.example.chapter06_server.UserInfoProvider/user/#
            // 删除单行
            case USER:
                // 获取Uri请求路径中的id(是数据编号，用来请求单条数据。如果是多条,这个字段忽略)
                String id = uri.getLastPathSegment();

                SQLiteDatabase writableDatabase2 = dbHelper.getWritableDatabase();
                count = writableDatabase2.delete(UserDBHelper.TABLE_NAME,"_id = ?",new String[]{id});
                writableDatabase2.close();
                break;
        }

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}