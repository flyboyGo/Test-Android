package com.example.chapter12_network_communication.okHttp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chapter12_network_communication.BuildConfig;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InterceptorUnitTest {

    @Test
    public void interceptorTest(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(new File("C:\\Users\\flyboy\\Desktop\\Android\\cache.txt"),1024*1024))
                .addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                // 前置处理
                Request request = chain.request().newBuilder()
                        .addHeader("os", "android")
                        .addHeader("version", BuildConfig.VERSION_NAME)
                        .build();

                Response response = chain.proceed(request);
                // 后置处理
                return response;
            }
        }).addNetworkInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                System.out.println("version == " + chain.request().header("version"));
                return chain.proceed(chain.request());
            }
        }).build();

        Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void interceptorTest2() throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        // 在拦截器链中获取请求对象(request)
                        Request request = chain.request();
                        long curTime = System.currentTimeMillis();
                        System.out.println("intercept : REQUEST = " + request.toString());

                        // 拦截器链继续传递请求(request)
                        Response response = chain.proceed(request);

                        // response.body().string(); // 错误写法,提前获取response对象的输出流
                        System.out.println("intercept : RESPONSE = " + response.toString());
                        System.out.println("intercept : spend time = " + (System.currentTimeMillis() - curTime) + " ms");

                        return response;
                    }
                }).build();

        Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build();
        Call call = okHttpClient.newCall(request);
        call.execute();
    }
}
