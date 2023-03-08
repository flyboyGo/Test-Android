package com.example.chapter06_client;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chapter06_client.entity.User;

public class ContentWriterActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_writer);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                ContentValues values = new ContentValues();
                values.put(UserInfoContent.USER_NAME, et_name.getText().toString());
                values.put(UserInfoContent.USER_AGE, Integer.parseInt(et_age.getText().toString()));
                values.put(UserInfoContent.USER_HEIGHT, Integer.parseInt(et_height.getText().toString()));
                values.put(UserInfoContent.USER_WEIGHT, Float.parseFloat(et_weight.getText().toString()));
                values.put(UserInfoContent.USER_MARRIED, ck_married.isChecked());

                // 获取内容解析器ContentResolver，在系统中通过Uri访问指定的ContentProvider
                getContentResolver().insert(UserInfoContent.CONTENT_URI,values);
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete:
                int count = 0;
                // 「id 」 是数据编号，用来请求单条数据。如果是多条,这个字段忽略

                // content://com.example.chapter06_server.provider.UserInfoProvider/user/2
                // 删除单行
                // 直接在Uri请求路径的最后添加id(是数据编号，用来请求单条数据。如果是多条,这个字段忽略)
//                Uri uri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI,2);
//                count = getContentResolver().delete(uri,null,null);

                // content://com.example.chapter06_server.provider.UserInfoProvider/user
                // 删除多行
                count = getContentResolver().delete(UserInfoContent.CONTENT_URI,"name = ?",new String[]{"Jack"});
                if(count > 0)
                {
                    Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_read:
                // 获取内容解析器ContentResolver，在系统中通过Uri访问指定的ContentProvider
                Cursor cursor = getContentResolver().query(UserInfoContent.CONTENT_URI, null, null, null, null, null);
                if(cursor != null)
                {
                    User user = null;
                    while (cursor.moveToNext())
                    {
                        user = new User();
                        user.id = cursor.getInt(cursor.getColumnIndex(UserInfoContent._ID));
                        user.name = cursor.getString(cursor.getColumnIndex(UserInfoContent.USER_NAME));
                        user.age = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_AGE));
                        user.height = cursor.getFloat(cursor.getColumnIndex(UserInfoContent.USER_HEIGHT));
                        user.weight = cursor.getFloat(cursor.getColumnIndex(UserInfoContent.USER_WEIGHT));
                        user.married =  cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_MARRIED)) == 1 ? true : false;
                        Log.d("test",user.toString());
                    }
                    cursor.close();
                }
                Toast.makeText(this,"查询成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}