package com.example.chapter12_network_communication.okHttp;

import android.Manifest;
import android.util.Log;

import com.example.chapter12_network_communication.util.PermissionUtil;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.Permission;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class UpLoadUnitTest {

    @Test
    public void uploadFileTest() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        File file = new File("C:\\Users\\flyboy\\Desktop\\Android\\info.txt");

        RequestBody requestBody = RequestBody.create(file, MediaType.parse("text/plain"));

        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), requestBody)
                .addFormDataPart("a","1")
                .build();

        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(multipartBody).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();

        System.out.println(response.body().string());
    }

    @Test
    public void uploadJsonTest() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = RequestBody.create("{\"a\":1,\"b\":2}", MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();

        System.out.println(response.body().string());
    }

    @Test
    public void uploadFormTest() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        FormBody formBody = new FormBody.Builder()
                .add("test","测试参数")
                .build();

        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();

        System.out.println(response.body().string());
    }
}
