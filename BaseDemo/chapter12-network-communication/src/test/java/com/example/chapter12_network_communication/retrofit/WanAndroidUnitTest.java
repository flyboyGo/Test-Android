package com.example.chapter12_network_communication.retrofit;

import androidx.annotation.NonNull;

import com.example.chapter12_network_communication.retrofit.bean.BaseResponse;
import com.google.gson.Gson;

import org.junit.Test;
import org.reactivestreams.Publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WanAndroidUnitTest {

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.wanandroid.com/").build();
    WanAndroidService wanAndroidService = retrofit.create(WanAndroidService.class);

    @Test
    public void loginTest() throws IOException {
        Call<ResponseBody> call = wanAndroidService.login("flyboy", "086319");
        Response<ResponseBody> response = call.execute();
        String result = response.body().string();
        System.out.println("result = " + result);

        // 手动进行数据转化(反序列化)
        BaseResponse baseResponse = new Gson().fromJson(result, BaseResponse.class);
        System.out.println(baseResponse.toString());
    }



    Retrofit retrofit2 = new Retrofit.Builder().baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create()) // 添加转换器(反序列化json数据)
            .build();
    WanAndroidService wanAndroidService2 = retrofit2.create(WanAndroidService.class);

    @Test
    public void loginConvertTest() throws IOException {
        Call<BaseResponse> call = wanAndroidService2.login2("flyboy", "086319");
        Response<BaseResponse> response = call.execute(); // 此时response的泛型类型是自定义的数据类型

        // 自动数据转化(利用转换器,完成反序列化)
        // response.body()返回的数据类型已被解析过一次,是我们自定义的数据类型
        BaseResponse baseResponse = response.body();
        System.out.println(baseResponse);
    }


    // 临时保存服务器发送的cookie数据(内存中),不保存成文件
    Map<String,List<Cookie>> cookies = new HashMap<>();
    Retrofit retrofit3 = new Retrofit.Builder().baseUrl("https://www.wanandroid.com/")
            // Retrofit添加自己定制的okHttp
            .callFactory(new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() { // 添加cookie信息
                        @Override
                        public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
                            cookies.clear();
                            cookies.put(httpUrl.host(),list);
                        }

                        @NonNull
                        @Override
                        public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                            List<Cookie> cookieList = WanAndroidUnitTest.this.cookies.get(httpUrl.host());
                            return cookieList == null ? new ArrayList<>() : cookieList;
                        }
                    })
                    // .cache() // 设置缓存
                    .build())
            .addConverterFactory(GsonConverterFactory.create()) // 添加转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加适配器
            .build();
    WanAndroidService wanAndroidService3 = retrofit3.create(WanAndroidService.class);


    @Test
    public void rxjavaTest(){
        wanAndroidService3.login3("flyboy","086319")
                .flatMap(new Function<BaseResponse, Publisher<ResponseBody>>() {
                    @Override
                    public Publisher<ResponseBody> apply(BaseResponse baseResponse) {
                        return wanAndroidService3.getArticle(0);
                    }
                })
                .subscribeOn(Schedulers.newThread()) // Android环境下(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io()) // 切换到子线程
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        System.out.println("==================" + responseBody.toString());
                    }
                });
    }

}
