package com.example.chapter12_network_communication.retrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.chapter12_network_communication.R;
import com.example.chapter12_network_communication.okHttp.OkHttpActivity;
import com.example.chapter12_network_communication.util.PermissionUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitActivity extends AppCompatActivity implements View.OnClickListener {

    private Retrofit retrofit;
    private HttpbinService httpbinService;

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        findViewById(R.id.send_network_ask).setOnClickListener(this);
        findViewById(R.id.btn_upload_image).setOnClickListener(this);

        retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/")
                // .callbackExecutor()
                .build();
        httpbinService = retrofit.create(HttpbinService.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_network_ask:
                // 使用Retrofit,发送网络数据请求
                retrofit2.Call<ResponseBody> retrofitCall = httpbinService.posts("flyboy", "086319");
                retrofitCall.enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        try {
                            Log.d("test","postASync: " + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // 切换到主线程中
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable throwable) {
                    }
                });
                break;
            case R.id.btn_upload_image:
                PermissionUtil.checkPermission(RetrofitActivity.this,PERMISSIONS,requestCode);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    private void uploadImageFile(String imagePath) {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestBody);

        Call<ResponseBody> call = httpbinService.uploadImageFile(part);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("test",response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}