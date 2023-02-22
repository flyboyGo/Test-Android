package com.example.rxjava;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginNetwork {

    private static  ApiService apiService;
    private static  Retrofit retrofit;

    private static void init(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.httpbin.org/")
                .client(okHttpClient)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static ApiService getService(){
        if(apiService == null){
            init();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
