package com.example.rxjava.module;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {

    @POST("post")
    @FormUrlEncoded
    Observable<ResponseBody> login(@Field("username") String username, @Field("password") String pwd);
}
