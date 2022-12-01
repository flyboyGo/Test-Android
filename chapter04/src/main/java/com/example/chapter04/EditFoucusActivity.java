package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditFoucusActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    EditText et_phone;
    EditText et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_foucus);

        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);

        // 设置密码框获取焦点监听事件
        et_password.setOnFocusChangeListener(this);

        /*
           编辑框点击两次后才会触发点击事件，因为第一次点击只触发焦点变更事件，第二次点击才触发点击事件。

           若要判断是否切换编辑框输入，应当监听焦点变更事件，而非监听点击事件。
           调用编辑框对象的setOnFocusChangeListener方法，即可在光标切换之时（获得光标和失去光标）触发焦点变更事件。
         */
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus)
        {
            String phone = et_phone.getText().toString();
            // 手机号码不足11位
            if(phone.equals(null) ||  phone.length() < 11)
            {
                // 手机号码输入框重新获得焦点
                et_phone.requestFocus();
                // 页面弹出文本
                Toast.makeText(this,"请输入11位手机号码",Toast.LENGTH_SHORT).show();
            }
        }
    }
}