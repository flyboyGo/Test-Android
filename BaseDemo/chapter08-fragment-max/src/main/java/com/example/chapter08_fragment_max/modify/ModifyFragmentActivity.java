package com.example.chapter08_fragment_max.modify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chapter08_fragment_max.R;
import com.example.chapter08_fragment_max.fragment.OperateFragment;

public class ModifyFragmentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_fragment);

        findViewById(R.id.fragment_add_1).setOnClickListener(this);
        findViewById(R.id.fragment_add_2).setOnClickListener(this);
        findViewById(R.id.fragment_add_3).setOnClickListener(this);
        findViewById(R.id.fragment_find_id).setOnClickListener(this);
        findViewById(R.id.fragment_find_tag).setOnClickListener(this);
        findViewById(R.id.fragment_remove).setOnClickListener(this);
        findViewById(R.id.fragment_replace).setOnClickListener(this);
        findViewById(R.id.fragment_show).setOnClickListener(this);
        findViewById(R.id.fragment_hide).setOnClickListener(this);
        findViewById(R.id.fragment_attach).setOnClickListener(this);
        findViewById(R.id.fragment_detach).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_add_1:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, OperateFragment.class, null,"myFragment").commit();
                break;
            case R.id.fragment_add_2:
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.add(R.id.fragment_container, OperateFragment.class, null,"myFragment")
                        .addToBackStack("myBackStack")
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case R.id.fragment_add_3:
                FragmentManager fragmentManager3 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.add(R.id.fragment_container, new OperateFragment(), null)
                        .commit();
                break;
            case R.id.fragment_find_id:
                FragmentManager fragmentManager4 = getSupportFragmentManager();

                Fragment fragment = fragmentManager4.findFragmentById(R.id.fragment_container);
                if (fragment != null)
                {
                    Toast.makeText(this, "找到了" + fragment.toString(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "没找到", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fragment_find_tag:
                FragmentManager fragmentManager5 = getSupportFragmentManager();

                Fragment fragment2 = fragmentManager5.findFragmentByTag("myFragment");
                if (fragment2 != null) {
                    Toast.makeText(this, "找到了" + fragment2.toString(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "没找到", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fragment_remove:
                FragmentManager fragmentManager6 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction4 = fragmentManager6.beginTransaction();
                Fragment fragment3 = fragmentManager6.findFragmentById(R.id.fragment_container);

                if (fragment3 != null)
                {
                    Toast.makeText(this, "找到了" + fragment3.toString()+", 并成功移除", Toast.LENGTH_SHORT).show();
                    fragmentTransaction4.remove(fragment3).commit();
                }
                else {
                    Toast.makeText(this, "没找到", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fragment_replace:
                FragmentManager fragmentManager7 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction5 = fragmentManager7.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("param1","fragment已被替换");
                fragmentTransaction5.replace(R.id.fragment_container,OperateFragment.class,bundle).commit();
                break;
            case R.id.fragment_show:
                FragmentManager fragmentManager8 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction6 = fragmentManager8.beginTransaction();
                Fragment fragment4 = fragmentManager8.findFragmentById(R.id.fragment_container);
                if(fragment4 != null)
                {
                    fragmentTransaction6.show(fragment4);
                }
                fragmentTransaction6.commit();
                break;
            case R.id.fragment_hide:
                FragmentManager fragmentManager9 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction7 = fragmentManager9.beginTransaction();
                Fragment fragment5 = fragmentManager9.findFragmentById(R.id.fragment_container);
                if(fragment5 != null)
                {
                    fragmentTransaction7.hide(fragment5);
                }
                fragmentTransaction7.commit();
                break;
            case R.id.fragment_attach:
                FragmentManager fragmentManager10 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction8 = fragmentManager10.beginTransaction();
                Fragment fragment6 = fragmentManager10.findFragmentById(R.id.fragment_container);
                if(fragment6 != null)
                {
                    fragmentTransaction8.attach(fragment6);
                }
                fragmentTransaction8.commit();
                break;
            case R.id.fragment_detach:
                FragmentManager fragmentManager11 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction9 = fragmentManager11.beginTransaction();
                Fragment fragment7 = fragmentManager11.findFragmentById(R.id.fragment_container);
                if(fragment7 != null)
                {
                    fragmentTransaction9.detach(fragment7);
                }
                fragmentTransaction9.commit();
                break;
        }
    }
}