package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ActFinishActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_finish);

        img = findViewById(R.id.iv_back);
        btn = findViewById(R.id.btn_finish);

        img.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back || view.getId() == R.id.btn_finish)
        {
            finish();
        }
    }
}