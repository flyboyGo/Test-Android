package com.example.rxjava;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("post")
    @FormUrlEncoded
    Observable<ResponseBody> login(@Field("username") String username, @Field("password") String pwd);
}
