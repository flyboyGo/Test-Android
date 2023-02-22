package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter05.util.FileUtil;

import java.io.File;

public class FileWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private String path;
    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_write);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);
        tv_text = findViewById(R.id.tv_text);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);

        String packageCodePath = getPackageCodePath();
        String packageResourcePath = getPackageResourcePath();
        Log.d("test", packageCodePath + " " + packageResourcePath);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();

                StringBuilder sb = new StringBuilder();
                sb.append("姓名:").append(name);
                sb.append("\n年龄:").append(age);
                sb.append("\n身高:").append(height);
                sb.append("\n体重:").append(weight);
                sb.append("\n婚否:").append(ck_married.isChecked() ? "是":"否");

                // 文件名
                String fileName = System.currentTimeMillis() + ".txt";
                // 文件路径
                String directory = null;

                // 外部存储空间(SD卡)

                // 获取外部存储的私有空间的路径(应用卸载，数据删除)
                // directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
                // /storage/emulated/0/Android/data/com.example.chapter05/files/Download/1675575804902.txt

                // 获取外部存储的公共空间的路径(需要在清单文件中添加读、写的合法权限)(应用卸载，数据不删除)
                // directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                // /storage/emulated/0/Download/1675576009305.txt

                // 内部私有存储空间(应用卸载，数据删除)(空间有限)(sharePreferences默认使用内部私有空间)
                directory = getFilesDir().toString();
                // /data/user/0/com.example.chapter05/files/1675576187486.txt

                // 拼接路径与文件名
                path = directory + File.separatorChar + fileName;

                // 输出日志
                Log.d("test",path);
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();

                // 写入文件
                FileUtil.saveText(path,sb.toString());
                break;

            case R.id.btn_read:
                tv_text.setText(FileUtil.readText(path));
                break;
        }
    }
}