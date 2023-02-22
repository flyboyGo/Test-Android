package com.example.chapter06_client;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chapter06_client.util.PermissionUtil;

import java.io.File;
import java.util.EventListener;

public class ProviderApkActivity extends AppCompatActivity implements View.OnClickListener {

    // Android11之后的权限请求
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_apk);

        findViewById(R.id.btn_install).setOnClickListener(this);
    }

    // 兼容Android11之前的版本
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE &&
                PermissionUtil.checkGrant(grantResults)) {
            installApk();
        }
    }

    @Override
    public void onClick(View v) {
        // Android 11 之后获取 MANAGE_EXTERNAL_STORAGE 权限
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            Log.d("test","Android 11+");
            // 检查使用拥有该权限
            checkAndInstall();
        }
        else
        {
            // Android11 之前
            // 如果有权限，直接安装，反之没有权限获取权限
            if(PermissionUtil.checkPermission(this,PERMISSIONS,PERMISSION_REQUEST_CODE)) {
                installApk();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkAndInstall() {
        // 检查是否拥有MANAGE_EXTERNAL_STORAGE 权限，没有则跳转到设置页面
        if(!Environment.isExternalStorageManager())
        {
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.fromParts("package", getPackageName(), null));
            startActivity(intent);
        }
        else
        {
            installApk();
        }
    }

    // 安装APK文件
    private void installApk() {
        String apkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "fileName.pak";
        Log.d("test","apkPath: " + apkPath);
        // 获取应用包管理器
        PackageManager packageManager = getPackageManager();
        // 获取apk文件的包信息
        PackageInfo info = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if(info == null)
        {
            Toast.makeText(this,"安装文件以损坏",Toast.LENGTH_SHORT).show();
            return;
        }

        // installer
        Uri uri = Uri.parse(apkPath);
        // 兼容Android7.0，把访问文件的Uri方式改为FileProvider
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            // 通过FileProvider获得文件的Uri的访问方式
            uri = FileProvider.getUriForFile(this, getString(R.string.file_provider), new File(apkPath));
            Log.d("test",String.format("new uri : %s",uri.toString()));
        }
        // 跳转
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Intent的接受者将被准许读取Intent 携带的URI数据
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置Uri的数据类型为APK文件
        // intent.setData(uri);
        // intent.setType("image/*");
        // 同时设置data与type，必须使用setDataAndType函数
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        // 启动系统自带的应用安装程序(设置权限)
        startActivity(intent);
    }
}