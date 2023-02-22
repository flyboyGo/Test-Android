package com.example.chapter12_network_communication.retrofit;

import android.util.Log;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AnnotationUnitTest {

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/")
            .build();
    HttpbinService httpbinService = retrofit.create(HttpbinService.class);

    @Test
    public void queryMapTest() throws IOException {
        Map<String,String> map = new HashMap<>();
        map.put("username","flyboy");
        map.put("password","086319");
        Response<ResponseBody> response = httpbinService.gets2(map).execute();
        System.out.println(response.body().string());
    }

    @Test
    public void bodyTest() throws IOException {

        FormBody formBody = new FormBody.Builder()
                .add("username","flyboy")
                .add("password","086319")
                .build();

        Response<ResponseBody> response = httpbinService.postsBody(formBody).execute();
        System.out.println(response.body().string());
    }

    @Test
    public void pathTest() throws IOException {

        Response<ResponseBody> response = httpbinService.postsPath("post","android","flyboy","086319").execute();
        System.out.println(response.body().string());
    }

    @Test
    public void headersTest() throws IOException {
        Call<ResponseBody> response = httpbinService.postsWithHeaders();
        System.out.println(response.execute().body().string());
    }

    @Test
    public void urlTest() throws IOException {
        Response<ResponseBody> response = httpbinService.postsUrl("https://www.httpbin.org/post").execute();
        System.out.println(response.body().string());
    }



    // 自定义拦截器
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            System.out.println("message = " + message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

    // 自定义retrofit2的okHttp
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // 添加拦截器
            .build();

    Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://www.httpbin.org/")
            .client(okHttpClient) // 添加自定义的OkHttpClient
            .addConverterFactory(ScalarsConverterFactory.create()) //添加转换器
            .build();

    HttpbinService httpbinService2 = retrofit2.create(HttpbinService.class);

    @Test
    public void converterTest(){
        Call<String> call = httpbinService2.gets3("flyboy", "086319");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // response已被解析过一次,解析为String
                System.out.println("response = " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Response<String> response = null;
//                try {
//                    response = call.execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                // response已被解析过一次,解析为String
//                System.out.println("response = " + response.body());
//            }
//        }).start();

    }

}
