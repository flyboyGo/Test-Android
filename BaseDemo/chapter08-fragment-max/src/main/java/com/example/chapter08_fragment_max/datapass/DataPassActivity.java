package com.example.chapter08_fragment_max.datapass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter08_fragment_max.R;
import com.example.chapter08_fragment_max.fragment.DataPassFragment;

public class DataPassActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pass);

        DataPassFragment dataPassFragment = new DataPassFragment();
        // 方式一:
        dataPassFragment.setFragmentDataChangeListener(new DataPassFragment.OnFragmentDataChangeListener() {
            @Override
            public void onFragmentDataChange(String data) {
                tv_show.setText(data);
            }
        });

        // commit()是异步的，不一定立即生效
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_data,dataPassFragment)
                .commitNow();


        // 方式二：
        // 也可以FragmentManager的findFragmentById,获取指定的fragment,来调用其中的设置方法
//        DataPassFragment fragment =(DataPassFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        if(fragment != null)
//        {
//            fragment.setFragmentDataChangeListener(new DataPassFragment.OnFragmentDataChangeListener() {
//                @Override
//                public void onFragmentDataChange(String data) {
//                    tv_show.setText(data);
//                }
//            });
//        }

        findViewById(R.id.btn_construct).setOnClickListener(this);
        findViewById(R.id.btn_common_function).setOnClickListener(this);
        findViewById(R.id.btn_argument).setOnClickListener(this);
        findViewById(R.id.btn_interface).setOnClickListener(this);
        findViewById(R.id.btn_fragment_between).setOnClickListener(this);

        tv_show = findViewById(R.id.tv_fragment_data);
    }

    // 接收fragment传递过来的数据
    public void setFragmentData(String data)
    {
        tv_show.setText(data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_construct:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_data, new DataPassFragment("这是构造函数传递的数据"))
                        .commit();
                break;
            case R.id.btn_common_function:
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_data);

                if(fragment != null)
                {
                    ((DataPassFragment)fragment).setParam1("这是普通方法传递的数据");
                }
                break;
            case R.id.btn_argument:
                // 方式一:
                // DataPassFragment dataPassFragment = DataPassFragment.newInstance("这是通过argument传递的数据", null);

                // 方式二:
                Bundle bundle = new Bundle();
                bundle.putString("param1","这是通过argument传递的数据");
                DataPassFragment dataPassFragment = new DataPassFragment();
                dataPassFragment.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_data, dataPassFragment)
                        .commit();
                break;
            case R.id.btn_interface:
                if(dataChangeListener != null)
                {
                    dataChangeListener.onDataChange("这是通过接口传递的数据");
                }
                break;
            case R.id.btn_fragment_between:
                Intent intent = new Intent(DataPassActivity.this,FragmentDataPassBetweenActivity.class);
                startActivity(intent);
                break;
        }
    }

    private OnDataChangeListener dataChangeListener;

    public void setOnDataChangeListener(OnDataChangeListener dataChangeListener)
    {
        this.dataChangeListener = dataChangeListener;
    }

    public interface OnDataChangeListener
    {
        void onDataChange(String data);
    }
}