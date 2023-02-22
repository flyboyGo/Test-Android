package com.example.chapter04;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlterDialogActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_dialog);

        findViewById(R.id.btn_alert).setOnClickListener(this);
        tv_alert = findViewById(R.id.tv_alert);
    }

    @Override
    public void onClick(View view) {
        // 创建提醒对话框的构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置对话框的标题
        builder.setTitle("尊敬的用户");
        // 设置对话框的内容文本
        builder.setMessage("您真的要卸载我吗?");
        // 设置对话框的肯定按钮文本及其文本监听器
        builder.setPositiveButton("残忍卸载", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tv_alert.setText("虽然依依不舍，但是只能离开");
                System.out.println(i);
            }
        });

        // 设置对话框的否定按钮文本及其文本监听器i
        builder.setNegativeButton("我再想一想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tv_alert.setText("再陪您一个春夏秋冬");
                System.out.println(i);
            }
        });

        // 根据构建器构建提醒对话框对象
        AlertDialog dialog = builder.create();
        // 显示对话框
        dialog.show();
    }
}