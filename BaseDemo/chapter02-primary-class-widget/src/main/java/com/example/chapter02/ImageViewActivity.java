package com.example.chapter02;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {

    private ImageView iv, iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        iv = findViewById(R.id.iv);

        iv2 = findViewById(R.id.iv2);
        iv2.setImageResource(R.drawable.me);
        iv2.setScaleType(ImageView.ScaleType.CENTER_CROP);

    }
}