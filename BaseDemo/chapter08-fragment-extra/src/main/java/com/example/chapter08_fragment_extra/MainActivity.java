package com.example.chapter08_fragment_extra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /*
         Fragment
           1、fragment有自己独立的生命周期
           2、fragment必须依托activity才能存活,无法独立存在;
               所以fragment受制于activity,activity暂停,fragment也暂停;activity销毁，fragment也销毁;
           3、一个activity可以包含多个fragment
           4、一个fragment可以被多个activity重复利用
           5、因为fragment有自己独立的生命周期,所以每个fragment可以单独与用户进行各项交互
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}