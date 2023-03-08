package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class IntentFilerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_filer);

        /*
        使用隐式意图注意事项：
          1、在Activity中设置的动作、类别、数据必须与intent-filter中配置的动作、类别、数据相同
          2、当data中既配置了scheme，又配置了mimeType时，在Activity中设置数据时只能使用setDataAndType()方法进行设置
          3、一个activity中可以有多个intent-filter。
             当activity中有多个intent-filter，Activity中的intent是根据匹配的action、category以及data来确定是与哪个页面对应的

          什么时候用显示意图，什么时候用隐式意图？
             1、开启自己应用的界面时，用显示意图
             2、开启其他应用的界面时( "系统应用" )，用隐式意图

         显示意图与隐式意图哪个更安全？
             显示意图比隐式意图更加安全，因为显示意图无法根据设置动作、数据、类别等方法进行直接访问

         Intent intent = new Intent(this,MainActivity.class);
           intent.setType();
           intent.setData();
           intent.setDataAndType()
           setType、setData只能单独使用,如需要同时设置,需要使用setDataAndType
         */
    }
}