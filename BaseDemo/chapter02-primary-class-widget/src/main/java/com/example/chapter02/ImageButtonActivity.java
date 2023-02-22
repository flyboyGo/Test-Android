package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ImageButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_button);


        /*
          ImageButton是显示图片的图像按钮，但它继承自ImageView，而非继承Button。

          ImageButton和Button之间的区别有：
          Button既可显示文本也可显示图片，ImageButton只能显示图片不能显示文本。
          ImageButton上的图像可按比例缩放，而Button通过背景设置的图像会拉伸变形。
          Button只能靠背景显示一张图片，而ImageButton可分别在前景和背景显示图片，从而实现两张图片叠加的效果。

          在某些场合，有的字符无法由输入法打出来，或者某些文字以特殊字体展示，就适合先切图再放到ImageButton。例如：开平方符号等等。

          ImageButton与ImageView之间的区别有：

          ImageButton有默认的按钮背景，ImageView默认无背景。
          ImageButton默认的缩放类型为center，而ImageView默认的缩放类型为fitCenter
         */
    }
}