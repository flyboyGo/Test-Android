package com.example.chapter13_room.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chapter13_room.R;

public class SPActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spactivity);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
    }

    /**
     * @param
     *     // 参数1 : sp的名字
     *     // 参数2 : sp保存的时候的模式 追加(每次保存都会追加, Context.MODE_APPEND)，
     *                               常规(每次保存都会更新, Context.MODE_PRIVATE)
     *
     *     @Override
     *     public SharedPreferences getSharedPreferences(String name, int mode) {
     *         return mBase.getSharedPreferences(name, mode);
     *     }
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                SharedPreferences sp = getSharedPreferences("simple_sp", Context.MODE_PRIVATE);
                // 获取编辑器
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name","李鹏飞");
                editor.putInt("age",18);
                // 提交修改(批量提交)
                editor.commit();

                //方式二:(单个提交)
                editor.putString("address","江苏盐城").apply();

                break;
            case R.id.btn_read:
                SharedPreferences sp2 = getSharedPreferences("simple_sp", Context.MODE_PRIVATE);
                // 获取保存的数据时,需要设置获取时的默认值
                String name = sp2.getString("name","anybody");
                int age = sp2.getInt("age",0);
                String address = sp2.getString("address", "江苏盐城");

                Toast.makeText(this, "name : " + name + ", age : " + age + " ,address : " + address, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}