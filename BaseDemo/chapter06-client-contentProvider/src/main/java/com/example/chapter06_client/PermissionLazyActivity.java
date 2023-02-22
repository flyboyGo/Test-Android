package com.example.chapter06_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chapter06_client.util.PermissionUtil;

public class PermissionLazyActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String[]PERMISSIONS_CONTACTS = new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };

    private static final String[]PERMISSIONS_SMS = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS
    };

    // 通讯录请求码
    private  static final int REQUEST_CODE_CONTACTS = 1;
    // 短信请求码
    private  static final int REQUEST_CODE_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_lazy);

        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);

        /*
            Android系统为了防止某些App滥用权限，从6.0开始引入了运行时权限管理机制，允许App在运行过程中动态检查是否拥有某项权限，
            一旦发现缺少某种必需的权限，则系统会自动弹出小窗提示用户去开启该权限。
         */
        /*
           检查App是否开启了指定权限
                调用ContextCompat的checkSelfPermission方法。
           请求系统弹窗，以便用户选择是否开启权限
                调用ActivityCompat的requestPermissions方法，即可命令系统自动弹出权限申请窗口。
           判断用户的权限选择结果
                重写活动页面的权限请求回调方法onRequestPermissionsResult，在该方法内部处理用户的权限选择结果。
         */
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_contact:
                PermissionUtil.checkPermission(this,PERMISSIONS_CONTACTS,REQUEST_CODE_CONTACTS);
                break;
            case R.id.btn_sms:
                PermissionUtil.checkPermission(this,PERMISSIONS_SMS,REQUEST_CODE_SMS);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_CONTACTS:
                if(PermissionUtil.checkGrant(grantResults)){
                    Toast.makeText(this,"通讯录相关权限开启成功",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this,"通讯录相关权限开启失败",Toast.LENGTH_SHORT).show();
                    jumpToSettings();
                }
                break;
            case REQUEST_CODE_SMS:
                if(PermissionUtil.checkGrant(grantResults))
                {
                    Toast.makeText(this,"短信相关权限开启成功",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this,"短信相关权限开启失败",Toast.LENGTH_SHORT).show();
                    jumpToSettings();
                }
                break;
        }
    }

    // 跳转到应用设置界面
    private void jumpToSettings()
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        // uri = package:com.example.chapter06_client
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}