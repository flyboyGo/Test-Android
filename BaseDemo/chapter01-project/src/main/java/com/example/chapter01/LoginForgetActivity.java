package com.example.chapter01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {
    private String phone;
    private String verifyCode = "";
    private EditText et_password_first;
    private EditText et_password_second;
    private EditText et_verifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);

        // 从上一个页面获取要修改的密码的手机号码
        phone = getIntent().getStringExtra("phone");

        findViewById(R.id.btn_verifycode).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);

        et_password_first = findViewById(R.id.et_password_first);
        et_password_second = findViewById(R.id.et_password_second);

        et_verifyCode = findViewById(R.id.et_verifycode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 点击了获取验证码按钮
            case R.id.btn_verifycode:
                // 生成六位随机数字的验证码
                verifyCode = String.format("%6d", new Random().nextInt(999999));
                // 以下弹出提醒对话框,提示用户记住验证码
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("验证码");
                builder.setMessage("手机号"+phone + ",本次验证码是 " +verifyCode+ " ,请输入验证码");
                builder.setPositiveButton("好的",null);

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.btn_confirm:
                // 点击了确定按钮后
                String password_first = et_password_first.getText().toString();
                String password_second = et_password_second.getText().toString();
                if(password_first.length() < 6)
                {
                    Toast.makeText(this,"请输入正确的密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password_first.equals(password_second))
                {
                    Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!verifyCode.equals(et_verifyCode.getText().toString()))
                {
                    Toast.makeText(this,"两次输入的验证码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                // 密码修改成功,返回成功密码
                Toast.makeText(this,"密码修改成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("new_password",password_first);
                setResult(Activity.RESULT_OK,intent);
                finish();
                break;
        }
    }
}