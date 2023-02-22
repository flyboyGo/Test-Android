package com.example.combinedview_digitalkeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        LoginPageView loginPageView = this.findViewById(R.id.LoginPageView);
        loginPageView.setOnLoginPageActionListener(new LoginPageView.OnLoginPageActionListener() {
            @Override
            public void onGetVerifyCodeClick(String phoneNum) {
                // 获取验证码
                // 用户提示
                Toast.makeText(MainActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onConfirmProtocolClick(boolean isChecked) {
                // 打开协议页面
                // 用户提示
                if(isChecked){
                    Toast.makeText(MainActivity.this,"同意用户协议",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"取消用户协议",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLoginClick(String verifyCode, String phoneNum) {
                // 登录
                // 检查验证码
                App.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 用户提示
                        Toast.makeText(MainActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                        loginPageView.onVerifyCodeError();
                    }
                },4000);
            }
        });
    }
}