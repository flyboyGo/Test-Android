package com.example.chapter08_fragment_extra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chapter08_fragment_extra.Interface.IFragmentCallback;
import com.example.chapter08_fragment_extra.fragment.DynamicFragment;
import com.example.chapter08_fragment_extra.fragment.DynamicFragment2;
import com.example.chapter08_fragment_extra.fragment.LifeCycleFragment;

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener {

    /*
        (1)动态添加fragment的步骤
           1、创建一个待处理的fragment
           2、获取FragmentManager,一般都是通过getSupportFragmentManager()
           3、开启一个事务transaction,一般调用fragmentManager的beginTransaction()
           4、使用transaction进行fragment的替换
           5、提交事务

        (2)
          1、activity与fragment的通信(单向)(原生的Bundle)
          2、activity与fragment的通信(双向)(java语言中类与类常用的通信方案：接口)
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        findViewById(R.id.btn_change).setOnClickListener(this);
        findViewById(R.id.btn_replace).setOnClickListener(this);
        findViewById(R.id.btn_create).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create:
                LifeCycleFragment lifeCycleFragment = LifeCycleFragment.newInstance();
                replaceFragment(lifeCycleFragment);
                break;
            case R.id.btn_change:
                // activity与fragment通讯(单向)(原生通过bundle)!!!!!!!!!!!!!!!!!!!!!!!!!
                // putParcelable可以传递javaBean(序列化)
                Bundle bundle = new Bundle();
                bundle.putString("msg","Activity connect with Fragment");
                DynamicFragment fragment = new DynamicFragment();
                // fragment装填数据(Bundle)
                fragment.setArguments(bundle);
                replaceFragment(fragment);
                break;
            case R.id.btn_replace:
                // activity与fragment的通信(双向)(java语言中类与类常用的通信方案：接口)
                // 实例化接收通信对象
                IFragmentCallback callback = new IFragmentCallback() {

                    @Override
                    public void sendMessageToActivity(String msg) {
                        Toast.makeText(DynamicActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    // 缺陷(fragment获取数据时,activity的数据必须存在;fragment一旦获取数据后,activity数据发生改变时,数据无法更新;)
                    // eventBus、LiveDate(观察者模式,解决方案)
                    @Override
                    public String getMsgFromActivity(String msg) {
                        if (msg != null)
                        {
                            return "msg from activity";
                        }
                        return "msg is null";
                    }
                };
                // 实例化fragment
                DynamicFragment2 fragment2 = new DynamicFragment2();
                // 给fragment中的通讯接口对象赋值
                fragment2.setFragmentCallback(callback);
                replaceFragment(fragment2);
                break;
        }
    }

    // 动态切换fragment
    private void replaceFragment(Fragment fragment) {
        // fragment管理类
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 获取fragment管理类中的transaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // transaction负责对fragment的增加、删除、替换、隐藏等操作(操作是基于fragment的管理栈)
        transaction.replace(R.id.frameLayout,fragment);
        // 新的fragment进入,当前显示的fragment才入栈,栈中的fragment会比所有的fragment少一个,当前显示的fragment不在栈中
        // 将每一个fragment添加到栈中(返回时,fragment依次出栈,不会直接退出)
        transaction.addToBackStack(null);
        // transaction提交修改
        transaction.commit();
    }
}