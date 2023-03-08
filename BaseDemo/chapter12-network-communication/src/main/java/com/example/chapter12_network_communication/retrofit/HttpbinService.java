package com.example.chapter12_network_communication.retrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface HttpbinService {

    // https://www.httpbin.org/post  + 参数
    @POST("post")  // post请求方式的服务器接口名,一般情况参数名注解使用@Field
    @FormUrlEncoded // 表单数据形式携带数据
    Call<ResponseBody> posts(@Field("username") String username, @Field("password") String pwd);

    @POST("post")
    Call<ResponseBody> postsBody(@Body RequestBody body); // 自定义请求体(formbody,multipartbody),其他的请求接口的请求体是自动生成的,将用户传入数据，填充进去

    // https://www.httpbin.org/post/{path}
    @POST("post/{path}")  // path注解作用于请求参数中,将花括号中的参数进行替换   header注解,添加请求头
    @FormUrlEncoded
    Call<ResponseBody> postsPath(@Path("path") String path, @Header("os")String os, @Field("username") String username, @Field("password") String pwd);

    @Headers({"os:android","version:1.0"}) // headers注解,同时添加多个请求头
    @POST("post")
    Call<ResponseBody> postsWithHeaders();

    @POST
    Call<ResponseBody> postsUrl(@Url String url); // url注解,直接请求url对应的请求地址(完整的域名)

    @POST("post")
    @Multipart
    Call<ResponseBody> uploadImageFile(@Part MultipartBody.Part part);



    @HTTP(method = "get", path = "get", hasBody = false)
    Call<ResponseBody>https(@Query("username") String username, @Query("password") String pwd);

    @GET("get")
    Call<String> gets3(@Query("username") String username, @Query("password") String pwd);


    @GET("get") // GET请求方式,一般情况参数名注解使用@Query
    Call<ResponseBody>gets(@Query("username") String username, @Query("password") String pwd);

    @GET("get")
    Call<ResponseBody>gets2(@QueryMap Map<String,String> map);

}
