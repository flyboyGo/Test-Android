package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MetaDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_data);

        /*
           元数据是一种描述其他数据的数据，它相当于描述固定活动的参数信息。
           在activity节点内部添加meta-data标签，通过属性name指定元数据的名称，通过属性value指定元数据的值。
         */

        /*
           在Java代码中，获取元数据信息的步骤分为下列三步：
           调用getPackageManager方法获得当前应用的包管理器；
           调用包管理器的getActivityInfo方法获得当前活动的信息对象；
           活动信息对象的metaData是Bundle包裹类型，调用包裹对象的getString即可获得指定名称的参数值；
         */

        TextView tv = findViewById(R.id.tv_meta);

        // 获取应用包管理器
        PackageManager packageManager = getPackageManager();
        try {
            ActivityInfo info = packageManager.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            // 获取活动附加的元数据信息
            Bundle bundle = info.metaData;
            String weather = bundle.getString("weather");
            tv.setText(weather);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}