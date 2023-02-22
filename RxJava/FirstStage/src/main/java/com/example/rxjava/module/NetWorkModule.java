package com.example.rxjava.module;

import com.example.rxjava.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(ApplicationComponent.class)
@Module
public class NetWorkModule {

    @Singleton
    @Provides
    OkHttpClient getOkHttpClient(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        return okHttpClient;
    }


    @Singleton
    @Provides
    Retrofit getRetrofit(OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.httpbin.org/")
                .client(client)
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    Service getService(Retrofit retrofit){

        Service service = retrofit.create(Service.class);
        return service;
    }
}
