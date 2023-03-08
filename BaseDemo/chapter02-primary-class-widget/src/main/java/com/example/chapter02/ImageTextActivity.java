package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ImageTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_text);

        /*
          同时展示文本与图像的可能途径包括：
         （1）利用LinearLayout对ImageView和TextView组合布局。
         （2）通过按钮控件Button的drawable***属性设置文本周围的图标。
             drawableTop：指定文字上方的图片。
             drawableBottom：指定文字下方的图片。
             drawableLeft：指定文字左边的图片。
             drawableRight：指定文字右边的图片。
             drawablePadding：指定图片与文字的间距
         */
    }
}