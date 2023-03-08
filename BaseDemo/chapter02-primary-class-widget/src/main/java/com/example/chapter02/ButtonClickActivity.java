package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter02.util.DateUtil;

public class ButtonClickActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn, btn2, btn3;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_click);

        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        tv = findViewById(R.id.tv);

        // 1、直接在布局文件中添加onClick事件，硬编码方式

        // 2、使用匿名类
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String desc = String.format("%s 你点击了按钮: %s", DateUtil.getNowTime(), ((Button)view).getText());
//                tv.setText(desc);
//            }
//        });


        // 3、自定义类实现View中的监听器接口，再实例化自定义对象，给控件设置监听器
        btn.setOnClickListener(new MyOnClickListener(tv));


        // 4、使用公共的监听器
        btn2.setOnClickListener(this);


        // 指定按钮长按的点击监听事件
        btn3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String desc = String.format("%s 你点击了按钮: %s", DateUtil.getNowTime(), ((Button)view).getText());
                tv.setText(desc);
                return true; //false 指定 消耗 长按事件,不传递给父容器(不冒泡)(如果容器也指定的长按事件的监听器)
            }
        });

        //

    }

    // 静态的内部类，防止内存泄露
    static class MyOnClickListener implements View.OnClickListener{

        //静态内部类，不可以使用使用非静态的外部属性,但可以在构造时传入，作为其属性使用
        private final TextView textView;

        public MyOnClickListener(TextView textView)
        {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            String desc = String.format("%s 你点击了按钮: %s", DateUtil.getNowTime(), ((Button)view).getText());
            textView.setText(desc);
        }
    }

    // 本类直接实现View中的监听器接口，给控件设置公共监听器
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn2)
        {
            String desc = String.format("%s 你点击了按钮: %s", DateUtil.getNowTime(), ((Button)view).getText());
            tv.setText(desc);
        }
    }
}