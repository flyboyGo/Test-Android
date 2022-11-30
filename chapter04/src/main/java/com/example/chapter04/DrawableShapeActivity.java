package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class DrawableShapeActivity extends AppCompatActivity implements View.OnClickListener {
    private View v_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_shape);

        v_content = findViewById(R.id.v_content);
        findViewById(R.id.btn_oval).setOnClickListener(this);
        findViewById(R.id.btn_rect).setOnClickListener(this);

        // v_content 的背景设置为圆角矩形
        v_content.setBackgroundResource(R.drawable.shape_rect_gold);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btn_rect:
                v_content.setBackgroundResource(R.drawable.shape_rect_gold);
                break;
            case  R.id.btn_oval:
                v_content.setBackgroundResource(R.drawable.shape_oval_rose);
                break;
        }
    }
}