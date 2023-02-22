package com.example.chapter01;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter01.util.ViewUtil;

import java.util.EmptyStackException;
import java.util.Random;

public class LoginMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget;
    private CheckBox ck_remember;
    private EditText et_phone;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private ActivityResultLauncher<Intent> register;
    private Button btn_login;
    private String mPassword = "086319";
    private String verifyCode = "";
    private SharedPreferences loginConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        RadioGroup rg_login = findViewById(R.id.rg_login);
        // 给rg_login添加单选监听器事件
        rg_login.setOnCheckedChangeListener(this);

        tv_password = findViewById(R.id.tv_password);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        btn_login = findViewById(R.id.btn_login);

        // 给手机、密码输入框添加文本变化监听事件
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));

        // 给忘记密码按钮添加监听事件
        btn_forget.setOnClickListener(this);

        // 注册页面信息回传
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                if(intent != null && result.getResultCode() == Activity.RESULT_OK)
                {
                    // 用户密码已修改，更新密码d
                    mPassword = intent.getStringExtra("new_password");
                }
            }
        });

        btn_login.setOnClickListener(this);

        // 获取全局共享参数实例
        loginConfig = getSharedPreferences("LoginConfig", Activity.MODE_PRIVATE);

        // 加载全局共享参数
        reload();

    }

    private void reload() {
        Boolean isRemember = loginConfig.getBoolean("isRemember",false);
        if(isRemember)
        {
            String password = loginConfig.getString("password", null);
            String phone = loginConfig.getString("phone", null);
            et_password.setText(password);
            et_phone.setText(phone);

            ck_remember.setChecked(true);
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            // 选择密码登录方式
            case  R.id.rb_password:
                tv_password.setText(getString(R.string.login_password));
                et_password.setHint(getString(R.string.input_password));
                btn_forget.setText(getString(R.string.forget_password));
                ck_remember.setVisibility(View.VISIBLE);
                break;
            // 选择验证码登录方式
            case R.id.rb_verifycode:
                tv_password.setText(getString(R.string.verifycode));
                et_password.setHint(getString(R.string.input_verifycode));
                btn_forget.setText(getString(R.string.get_verifycode));
                ck_remember.setVisibility(View.GONE);
                et_password.setText("");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        if(phone.length() < 11)
        {
            Toast.makeText(this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            return;
        }

        switch (v.getId()){
            case R.id.btn_forget:
                // 选择了密码方式校验，此时要跳转到找回密码页面
                if(rb_password.isChecked())
                {
                    Intent intent = new Intent(this, LoginForgetActivity.class);
                    intent.putExtra("phone",phone);
                    register.launch(intent);
                }
                else if(rb_verifycode.isChecked())
                {
                    // 生成六位随机数字的验证码
                    verifyCode = String.format("%6d", new Random().nextInt(999999));
                    // 以下弹出提醒对话框,提示用户记住验证码
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("验证码");
                    builder.setMessage("手机号"+phone + ",本次验证码是 " +verifyCode+ " ,请输入验证码");
                    builder.setPositiveButton("好的",null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;
            case R.id.btn_login:
                // 选择了密码方式校验，此时要跳转到找回密码页面
                if(rb_password.isChecked()){
                    if(!mPassword.equals(et_password.getText().toString())){
                        Toast.makeText(this,"请输入正确的密码",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 提示用户登录成功,并判断用户是否需要保存密码
                    loginSuceess();
                }
                else if(rb_verifycode.isChecked())
                {
                    // 验证码方式校验
                    if(!verifyCode.equals(et_password.getText().toString())){
                        Toast.makeText(this,"请输入正确的验证码",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 提示用户登录成功
                    loginSuceess();
                }
                break;
        }
    }

    // 校验成功，登录成功
    private void loginSuceess() {
        // 判断用户是否需要保存密码
        if(ck_remember.isChecked())
        {
            // 获取全局参数的编辑器
            SharedPreferences.Editor editor = loginConfig.edit();
            editor.putString("phone",et_phone.getText().toString());
            editor.putString("password",et_password.getText().toString());
            editor.putBoolean("isRemember",ck_remember.isChecked());
            editor.commit();
        }

        String desc = String.format("你的手机号码是%s,恭喜你通过登录验证，点击确定按钮返回上一个页面",et_phone.getText().toString());

        // 弹出提醒对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 结束当前页面
                finish();
            }
        });
        builder.setNegativeButton("我再看看",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int maxLength;
        public HideTextWatcher(EditText et, int maxLength) {
            this.mView = et;
            this.maxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().length() == maxLength)
            {
                // 隐藏输入法软键盘
                ViewUtil.hideOneInputMethod(LoginMainActivity.this,mView);
            }
        }
    }
}