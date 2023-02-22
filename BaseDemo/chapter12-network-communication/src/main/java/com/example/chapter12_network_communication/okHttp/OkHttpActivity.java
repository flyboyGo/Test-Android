package com.example.chapter12_network_communication.okHttp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chapter12_network_communication.BuildConfig;
import com.example.chapter12_network_communication.R;
import com.example.chapter12_network_communication.retrofit.HttpbinService;
import com.example.chapter12_network_communication.util.PermissionUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {

    private OkHttpClient okHttpClient;

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        findViewById(R.id.get_sync).setOnClickListener(this);
        findViewById(R.id.get_async).setOnClickListener(this);
        findViewById(R.id.post_sync).setOnClickListener(this);
        findViewById(R.id.post_async).setOnClickListener(this);
        findViewById(R.id.post_upload_image_file).setOnClickListener(this);

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_sync:
                // 网络请求输入耗时操作，必须开启多线程
                new Thread(){
                    @Override
                    public void run() {
                        // 创建request对象(请求对象)(默认创建的就是get请求方式)
                        Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2")
                                .get()
                                .addHeader("os","android")
                                .addHeader("version", BuildConfig.VERSION_NAME)
                                .build();
                        // 创建一个准备好的call对象
                        Call call = okHttpClient.newCall(request);
                        try {
                            // 返回响应对象,同步请求(会发生一定程度的阻塞,所以需要创建子线程,防止阻碍主线程的执行)
                            Response response = call.execute();
                            Log.d("test","getSync: " + response.body().string());

                            // 切换到主线程中
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(OkHttpActivity.this,response.body().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.get_async:
                // 创建request对象(请求对象)
                Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2")
                        .get()
                        .build();
                // 创建一个准备好的call对象
                Call call = okHttpClient.newCall(request);
                // 异步请求(不会发生阻塞,不需要创建子线程)(内部会创建子线程,刷新ui必须切换到主线程中)
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful())
                        {
                            Log.d("test","getASync: " + response.body().string());
                            // 切换到主线程中
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(OkHttpActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.post_sync:

                // 协议规定POST提交的数据必须放在 "请求体中" ，但协议并没有规定数据必须使用什么编码方式
                new Thread(){
                    @Override
                    public void run() {
                        // 创建request对象(请求对象)(post请求方式)
                        FormBody formBody = new FormBody.Builder()
                                .add("a", "1")
                                .add("b", "2")
                                .build();
                        Request request = new Request.Builder().url("https://www.httpbin.org/post")
                                .post(formBody).build();
                        // 创建一个准备好的call对象
                        Call call = okHttpClient.newCall(request);
                        try {
                            Response response = call.execute();
                            Log.d("test","postSync: " + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.post_async:
                // 创建request对象(请求对象)(post请求方式)
                FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
                Request request2 = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();
                // 创建一个准备好的call对象
                Call call2 = okHttpClient.newCall(request2);
                call2.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful())
                        {
                            Log.d("test","postASync: " + response.body().string());
                        }
                    }
                });
                break;
            case R.id.post_upload_image_file:
                PermissionUtil.checkPermission(OkHttpActivity.this,PERMISSIONS,requestCode);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 获取图片的路径
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
            String imagePath = cursor.getString(columnIndex);
            // 上传网络图片
            uploadImageFile(imagePath);
            cursor.close();
        }

    }

    private void uploadImageFile(String imagePath)
    {
        File file = new File(imagePath);

        // 封装在data中
//        RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
//        MultipartBody multipartBody = new MultipartBody.Builder()
//                .addFormDataPart("file",file.getName(),requestBody)
//                .build();

        // 封装在data中
//        MultipartBody multipartBody = new MultipartBody.Builder()
//                .addFormDataPart("file",file.getName(),MultipartBody.create(file,MediaType.parse("multipart/form-data")))
//                .build();

        // 封装在file中
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",file.getName(),MultipartBody.create(file,MediaType.parse("multipart/form-data")))
                .build();

        Request request = new Request.Builder()
                .url("https://www.httpbin.org/post")
                .post(multipartBody)
                .build();
        
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("test",response.body().string());
            }
        });
    }
}