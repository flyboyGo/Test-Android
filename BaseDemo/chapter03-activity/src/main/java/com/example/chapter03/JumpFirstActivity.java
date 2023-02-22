package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class JumpFirstActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_first);
        findViewById(R.id.btn_jump_first).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // 创建一个意图对象，准备跳到指定的活动页面
        Intent intent = new Intent(JumpFirstActivity.this, JumpSecondActivity.class);
        // 栈中存在待跳转的活动实例时，则重新创建活动实例，并清除原有实例上方的所有实例
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /*
         如果一个Activity已经启动过，并且存在当前应用的Activity任务栈中，启动模式为singleTask、
         singleInstance、singleTop(此时已在任务栈顶端)，那么在此启动或回到这个Activity的时候，
         不会创建新的实例，也就是不会执行onCreate方法，而是执行onNewIntent方法。
     */

    /**
       启动标志的取值说明如下：
       1、Intent.FLAG_ACTIVITY_NEW_TASK：开辟一个新的任务栈

       此 Flag 跟 singleInstance 很相似，在给目标 Activity 设立此 Flag 后，会根据目标 Activity 的 affinity 进
       行匹配，如果已经存在与其affinity 相同的 task，则将目标 Activity 压入此 Task。反之没有的话，则新
       建一个 task，新建的 task 的 affinity 值与目标 Activity 相同，然后将目标 Activity 压入此栈。

      但它与 singleInstance 有不同的点，两点需要注意的地方：
      (1)、新的 Task 没有说只能存放一个目标 Activity，只是说决定是否新建一个 Task，
           而 singleInstance模式下新的 Task 只能放置一个目标 Activity。
      (2)、在同一应用下，如果 Activity 都是默认的 affinity，那么此 Flag 无效，而 singleInstance 默认情况
           也会创建新的 Task。


       2、Intent.FLAG_ACTIVITY_SINGLE_TOP：当栈顶为待跳转的活动实例之时，则重用栈顶的实例

       3、Intent.FLAG_ACTIVITY_CLEAR_TOP：当栈中存在待跳转的活动实例时，则重新创建一个新实例，并清除原实例上方的所有实例
         当设置此 Flag 时，目标 Activity 会检查 Task 中是否存在此实例，如果没有则添加压入栈。
         如果有，就将位于 Task 中的对应 Activity 其上的所有 Activity 弹出栈，此时有以下两种情况：
       (1)、如果同时设置 Flag_ACTIVITY_SINGLE_TOP ，则直接使用栈内的对应 Activity。
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
       (2): 没有设置，则将栈内的对应 Activity 销毁重新创建。


       4、Intent.FLAG_ACTIVITY_NO_HISTORY：栈中不保存新启动的活动实例

       5、Intent.FLAG_ACTIVITY_CLEAR_TASK：跳转到新页面时，栈中的原有实例都被清空
     */
}