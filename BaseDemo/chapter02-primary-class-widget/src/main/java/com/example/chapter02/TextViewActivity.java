package com.example.chapter02;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TextViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        // 通过控件的id,findViewById()函数可以获取指定控件对象
        TextView tv = findViewById(R.id.tv);

        // 通过setText函数设置控件的文本，并使用资源文件string.xml定义的字符串常量
        tv.setText(R.string.text_view);

        // 通过setTextSize()函数,设置字体的大小
        // 原来纯数字的setTextSize方法，内部默认字号单位为sp（COMPLEX_UNIT_SP），这也从侧面印证了之
        // 前的说法：sp才是Android推荐的字号单位。
        tv.setTextSize(30);


        /*
        在XML文件中则通过属性android:textColor指定文本颜色，
        色值由透明度alpha和RGB三原色（红色red、绿色green、蓝色blue）联合定义。

        色值有八位十六进制数与六位十六进制数两种表达方式，例如八位编码FFEEDDCC中，

        FF表示透明度，EE表示红色的浓度，DD表示绿色的浓度，CC表示蓝色的浓度。
        透明度为FF表示完全不透明，为00表示完全透明。
        RGB三色的数值越大，表示颜色越浓，也就越亮；数值越小，表示颜色越淡，也就越暗。
         */

        // 通过setTextColor()函数,
        // 将字体设置为系统自带的颜色
        tv.setTextColor(Color.GREEN);

        // 将字体设置为任意颜色(8位8进制)
        // 将文字的透明度设置为不透明的绿色,即正常的绿色
        TextView tv2 = findViewById(R.id.tv2);
        tv2.setTextColor(0xff00ff00);

        // 将字体设置为任意颜色(6位8进制)
        // 透明度不指明,默认为透明、看不见
        TextView tv3 = findViewById(R.id.tv3);
        tv3.setTextColor(0x00ff00);

        // 使用资源文件strings.xml中定义的颜色常量
        tv.setTextColor(getResources().getColor(R.color.purple_200));

        //设置背景颜色
        //系统自带的颜色
        tv.setBackgroundColor(Color.YELLOW);
        tv.setBackgroundColor(getResources().getColor(R.color.purple_200));
        //自定义的颜色
        tv2.setBackgroundResource(R.color.purple_200);
    }
}
