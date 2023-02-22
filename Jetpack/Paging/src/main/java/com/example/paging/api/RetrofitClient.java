package com.example.paging.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient Instance;
    private Retrofit retrofit;

    private RetrofitClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if(Instance == null)
        {
            Instance = new RetrofitClient();
        }
        return Instance;
    }

    public Api getApi(){
        Api api = retrofit.create(Api.class);
        return api;
    }
}
