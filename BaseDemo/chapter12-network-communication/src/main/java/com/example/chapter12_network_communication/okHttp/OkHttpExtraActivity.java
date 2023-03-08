package com.example.chapter12_network_communication.okHttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chapter12_network_communication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpExtraActivity extends AppCompatActivity implements View.OnClickListener {

    private OkHttpClient okHttpClient;
    private ImageView iv_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_extra);

        iv_header = findViewById(R.id.iv_header);
        findViewById(R.id.btn_download_image).setOnClickListener(this);
        findViewById(R.id.btn_download_file).setOnClickListener(this);
        findViewById(R.id.btn_download_multipart).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_download_image:
                // 下载网络图片
                okHttpClient = new OkHttpClient.Builder().build();
                Request request = new Request.Builder().url("Image_Uri").build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("test","图片下载失败");
                            }
                        });
                    }
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        // 成功
                        // 从响应对象体中获取数据输入流
                        InputStream inputStream = response.body().byteStream();
                        // 从返回的输入流中解码获得位图数据
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        // 设置控件显示位图
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv_header.setImageBitmap(bitmap);
                            }
                        });
                        // 获取响应对象体的类型
                        String type = response.body().contentType().toString();
                        // 获取响应对象体的大小
                        long contentLength = response.body().contentLength();
                    }
                });
                break;
            case R.id.btn_download_file:
                // 下载各式的文件
                okHttpClient = new OkHttpClient.Builder().build();
                Request request2 = new Request.Builder().url("File_Uri").build();
                Call call1 = okHttpClient.newCall(request2);
                call1.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        // 失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("test","文件下载失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        // 成功
                        // 获取响应体的文件类型
                        String type = response.body().contentType().toString();
                        // 获取响应体的文件大小
                        long length = response.body().contentLength();
                        // 获取、设置文件的下载路径
                        String path = String.format("%s/%s.APK",getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"当前系统时间");
                        // 通过获取的路径,创建文件(如果文件存在,在文件尾部添加true)
                        FileOutputStream fileOutputStream = new FileOutputStream(path,true);
                        // 从返回的输入流中读取字节数据并保存为本地文件
                        InputStream inputStream = response.body().byteStream();
                        byte [] buf = new byte[100 * 1024];
                        int sum = 0, len = 0;
                        while((len = inputStream.read(buf)) != -1)
                        {
                            fileOutputStream.write(buf,0,len);
                            sum += len;
                            int progress = (int)(sum * 1.0f / len * 100);
                            String detail = String.format("文件保存在%s, 已下载%d%", path, progress);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("test",detail);
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.btn_download_multipart:
                // 文本、文件组合上传

                okHttpClient = new OkHttpClient.Builder().build();
                // 创建分段内容的建造器对象
                MultipartBody.Builder builder = new MultipartBody.Builder();
                String username = "李鹏飞";
                String password = "086319";
                // 验证文本数据的合法性
                if(!TextUtils.isEmpty(username))
                {
                    // 往建造器对象中添加文本格式的分段数据
                    builder.addFormDataPart("username", username);
                    builder.addFormDataPart("password", password);
                }
                //往建造器对象中添加图像格式的分段数据
                File file = new File("");
                builder.addFormDataPart("image_file",file.getName(), RequestBody.create(file,MediaType.parse("image/*")));
                //根据建造器生成请求结构
                MultipartBody multipartBody = builder.build();
                // 创建一个POST方式的请求结构
                Request request3 = new Request.Builder().url("Image_URI").post(multipartBody).build();
                Call newCall = okHttpClient.newCall(request3);
                newCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("test","下载失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String resp = response.body().toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("test",resp);
                            }
                        });
                    }
                });
                break;
        }
    }
}