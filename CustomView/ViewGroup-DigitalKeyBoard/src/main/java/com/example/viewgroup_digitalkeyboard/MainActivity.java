package com.example.viewgroup_digitalkeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KeyPadView keyPadView = this.findViewById(R.id.keypad_view);
        keyPadView.setOnKeyClickListener(new KeyPadView.OnKeyClickListener() {
            @Override
            public void onNumberClick(int value) {
                Toast.makeText(getApplicationContext(),String.valueOf(value),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick() {
                Toast.makeText(getApplicationContext(),"删除",Toast.LENGTH_SHORT).show();
            }
        });
    }
}