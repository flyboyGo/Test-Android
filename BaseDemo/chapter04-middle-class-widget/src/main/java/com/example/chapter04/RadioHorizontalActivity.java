package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RadioHorizontalActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    TextView tv_result;
    TextView tv_result2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_horizontal);

        RadioGroup rg_sex = findViewById(R.id.rg_sex);
        tv_result = findViewById(R.id.tv_result);
        rg_sex.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            switch (checkedId) {
                case R.id.rb_male:
                    tv_result.setText("男性");
                    break;
                case R.id.rb_female:
                    tv_result.setText("女性");
                    break;
            }
    }
}