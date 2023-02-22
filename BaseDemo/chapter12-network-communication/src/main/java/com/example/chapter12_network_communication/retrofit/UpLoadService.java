package com.example.chapter12_network_communication.retrofit;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface UpLoadService {

    @POST("post")
    @Multipart
    Call<ResponseBody> upload(@Part MultipartBody.Part file);

    @POST("post")
    @Multipart
    Call<ResponseBody> uploads(@PartMap MultipartBody.Part file);


    @Streaming // 文件过大时，防止内存泄露
    @GET
    Call<ResponseBody> download(@Url String url);

    @Streaming // 文件过大时，防止内存泄露
    @GET
    Flowable<ResponseBody> downloadRxJava(@Url String url);
}
