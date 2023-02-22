package com.example.chapter12_network_communication.retrofit;

import com.example.chapter12_network_communication.retrofit.bean.BaseResponse;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WanAndroidService {

    @POST("user/login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String pwd);

    @POST("user/login") // 接口方法的返回结果，为自定义的数据类型
    @FormUrlEncoded
    Call<BaseResponse> login2(@Field("username") String username, @Field("password") String pwd);




    // io.reactivex.rxjava3.core.Flowable
    @POST("user/login")
    @FormUrlEncoded
    Flowable<BaseResponse> login3(@Field("username") String username, @Field("password") String pwd);

    @GET("lg/collect/list/{pageNum}/json")
    Flowable<ResponseBody> getArticle(@Path("pageNum") int pageNum);
}
