package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_input);
        findViewById(R.id.btn_loginInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginInputActivity.this,LoginSuccessActivity.class);
                // 设置启动标识：跳转到新的页面，栈中的原有实例都被清空，同时开辟新的任务的活动栈
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     *    1、栈顶复用模式 singleTop
     *       应用场景:
     *       适合开启渠道多、多应用开启调用的 Activity，通过这种设置可以避免已经创建过的 Activity 被重复创建，多数通过动态设置使用。
     *
     *
     *    2、栈内复用模式 singleTask与singleTop 模式相似，只不过 singleTop 模式是只是针对栈顶的元素，
     *       而 singleTask 模式下，如果task 栈内存在目标 Activity 实例，则将 task 内的对应 Activity 实例之上的所有 Activity 弹出栈，
     *      并将对应 Activity 置于栈顶，从新获得焦点，本质是调用onNewIntent方法启用原有的Activity实例。
     *      应用场景:
     *      程序主界面：我们肯定不希望主界面被创建多次，而且在主界面退出的时候退出整个 App 是最好的效果。
     *
     *
     *   3、全局唯一模式 singleInstance
     *    (1)、在该模式下，我们会为目标 Activity 创建一个新的 Task 栈，将目标 Activity 放入新的 Task，并让目标
     *         Activity获得焦点。新的Task 有且只有这一个 Activity 实例。
     *    (2)、如果已经创建过目标 Activity 实例，"则不会创建新的 Task",而是将以前创建过的 Activity 唤醒。
     *
     *    如果同时有动态和静态设置，那么动态的优先级更高。接下来我们来看一下如何动态的设置 Activity 启动模式。
     */
}