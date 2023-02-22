package com.example.a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a.view.InputNumberView;

public class InputNumberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_number);

        InputNumberView inputNumberView = this.findViewById(R.id.input_number_view);
        inputNumberView.setOnNumberChangeListener(new InputNumberView.OnNumberValueChangeListener() {
            @Override
            public void onNumberValueChange(int value) {
                Log.d("test","current value is " + value);
            }

            @Override
            public void onNumberMax(int value) {
                Log.d("test","maxValue == > " + value);
            }

            @Override
            public void onNumberMin(int value) {
                Log.d("test","minValue == > " + value);
            }
        });
    }
}