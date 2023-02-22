package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chapter02.util.Utils;

public class ViewBorder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_border);

        /*
          设置视图的宽高
          首先确保XML中的宽高属性值为wrap_content，接着打开该页面对应的Java代码，依序执行以下三个步骤：

          1、调用控件对象的getLayoutParams方法，获取该控件的布局参数。
          2、布局参数的width属性表示宽度，height属性表示高度，修改这两个属性值。
          3、调用控件对象的setLayoutParams方法，填入修改后的布局参数使之生效
         */

        TextView tv = findViewById(R.id.tv);
        ViewGroup.LayoutParams params = tv.getLayoutParams();
        //修改布局参数中的宽度数值，注意默认为px单位，需要把dp数值转变为px数值
        params.width = Utils.dipToPx(this, 300);
        //设置tv的布局参数
        tv.setLayoutParams(params);
    }
}