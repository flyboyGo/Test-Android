package com.example.chapter08_fragment_max;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chapter08_fragment_max.datapass.DataPassActivity;
import com.example.chapter08_fragment_max.modify.ModifyFragmentActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.to_static_fragment).setOnClickListener(this);
        findViewById(R.id.to_dynamic_fragment).setOnClickListener(this);
        findViewById(R.id.to_fragment_operate).setOnClickListener(this);
        findViewById(R.id.to_fragment_dataPass).setOnClickListener(this);
        findViewById(R.id.fragment_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.to_static_fragment:
               Intent intent = new Intent(MainActivity.this,StaticFragmentActivity.class);
               startActivity(intent);
                break;
            case R.id.to_dynamic_fragment:
                Intent intent2 = new Intent(MainActivity.this,DynamicFragmentActivity.class);
                startActivity(intent2);
                break;

            case R.id.to_fragment_operate:
                Intent intent3 = new Intent(MainActivity.this, ModifyFragmentActivity.class);
                startActivity(intent3);
                break;
            case R.id.to_fragment_dataPass:
                Intent intent4 = new Intent(MainActivity.this, DataPassActivity.class);
                startActivity(intent4);
                break;
            case R.id.fragment_view:
                Intent intent5 = new Intent(MainActivity.this,FragmentNavActivity.class);
                startActivity(intent5);
                break;
        }
    }
}