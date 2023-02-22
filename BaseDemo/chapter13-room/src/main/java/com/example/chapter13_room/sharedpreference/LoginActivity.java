package com.example.chapter13_room.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chapter13_room.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;
    private CheckBox ck_remember;
    private CheckBox ck_login;
    private Button btn_register;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        loadData();
    }

    private void init() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        ck_remember = findViewById(R.id.ck_remember);
        ck_login = findViewById(R.id.ck_login);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    private void loadData() {
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String username = sp.getString("username", null);
        et_username.setText(username);

        if(sp.getBoolean("isRemember",false))
        {
            String password = sp.getString("password", null);
            et_password.setText(password);
            ck_remember.setChecked(true);
        }

        if(sp.getBoolean("isLogin",false))
        {
            ck_login.setChecked(sp.getBoolean("isLogin",false));
            Toast.makeText(this, "我是自动登录", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if(checkData(username, password))
                {
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.commit();
                    Toast.makeText(this, "注册成功!!!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "注册失败,不符合规范!!!!!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, username + password, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_login:
                SharedPreferences sp2 = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                String username2 = sp2.getString("username", " ");
                String password2 = sp2.getString("password", " ");

                if(et_username.getText().toString().equals(username2) && et_password.getText().toString().equals(password2))
                {
                    Toast.makeText(this, "验证成功!!!!!", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor2 = sp2.edit();
                    editor2.putBoolean("isRemember",ck_remember.isChecked());
                    editor2.putBoolean("isLogin",ck_login.isChecked());
                    editor2.putString("username",et_username.getText().toString());
                    editor2.putString("password",et_password.getText().toString());
                    editor2.commit();
                }
                else
                {
                    Toast.makeText(this, "用户名或密码错误!!!!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean checkData(String username, String password) {
        String regex_password = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
        String regex_username = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";

        if(password.matches(regex_password) && username.matches(regex_username))
        {
            return true;
        }
        return false;
    }
}