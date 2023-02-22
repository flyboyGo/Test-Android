package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chapter05.util.FileUtil;

import java.io.File;

public class ImageWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_write);

        imageView = findViewById(R.id.iv_content);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                String fileName = System.currentTimeMillis() + ".jpg";

                // 获取当前app的外部私有空间
                path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar + fileName;

                // 从指定资源文件中获取位图对象
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.me);

                // 把位图对象保存为文件
                FileUtil.saveImage(path,bitmap);

                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_read:
                // 方式一
                // Bitmap bitmap2 = FileUtil.readImage(path);

                // 方式二
                // Bitmap bitmap2 = BitmapFactory.decodeFile(path);
                // imageView.setImageBitmap(bitmap2);

                // 方式三、直接调用setImageUri方法，设置图像视图的路径对象
                imageView.setImageURI(Uri.parse(path));
                break;
        }
    }
}