package com.example.chapter08_fragment_max;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.chapter08_fragment_max.fragment.DynamicFragment;

public class DynamicFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fragment);

        // activity第一次创建时,才会添加fragment,避免fragment重复添加
        // activity第一创建时,Bundle savedInstanceState是为空的
        // 动态添加fragment
        if(savedInstanceState == null)
        {
            // fragment管理器
            FragmentManager manager = getSupportFragmentManager();
            // fragment事务
            FragmentTransaction beginTransaction = manager.beginTransaction();
            // 新的fragment进入,当前显示的fragment才入栈,栈中的fragment会比所有的fragment少一个,当前显示的fragment不在栈中
            // 将每一个fragment添加到栈中(返回时,fragment依次出栈,不会直接退出)

            // 旧方法
            // 可以通过Fragment对象的setArgument传参、或者通过构造器直接传参
            // tag用于唯一标识fragment,便于后期的查找
            beginTransaction.add(R.id.fcv_layout, new DynamicFragment(),null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null);

            // 推荐方法
            Bundle bundle = new Bundle();
            bundle.putString("param1","动态fragment传递参数");
            bundle.putString("myParam","未使用默认参数");
            beginTransaction.add(R.id.fcv_layout, DynamicFragment.class,bundle)
                            .setReorderingAllowed(true)
                            .addToBackStack(null);
            // fragment提交
            beginTransaction.commit();
        }
    }
}