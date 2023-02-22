package com.example.viewmodel.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.viewmodel.R;

public class FirstActivity extends AppCompatActivity {

    private TextView textView;
    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        textView = findViewById(R.id.text_view);

        // 构建ViewModel对象
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MyViewModel.class);

        textView.setText(String.valueOf(viewModel.number));
    }

    // 点击事件函数
    public void plus(View view) {
        textView.setText(String.valueOf(++viewModel.number));
    }
}