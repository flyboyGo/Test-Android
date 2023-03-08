package com.example.chapter12_network_communication.okHttp;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CookieUnitTest {

    // 临时保存服务器发送的cookie数据(内存中),不保存成文件
    Map<String,List<Cookie>> cookieList = new HashMap<>();

    @Test
    public void cookie()
    {
        /*
             每次okhttp发起请求都会调用：加载saveFromResponse方法，将cookie放、返回给我们;
             每次请求返回是都会调用loadForRequest方法，我们将自己保存的cookie带入即可;
         */
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
                        if(cookieList != null && list.size() > 0)
                        {
                            cookieList.clear();
                            cookieList.put(httpUrl.host(),list);
                        }
                    }

                    /*
                        因为okhttp只提供cookie处理方法，不提供保存方法，所以需要自己保存,此处我们保存至手机本地内存中
                     */

                    @NonNull
                    @Override
                    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                        List<Cookie> cookieList = CookieUnitTest.this.cookieList.get(httpUrl.host());
                        return cookieList == null ? new ArrayList<>() : cookieList;
                    }
                }).build();

        FormBody formBody = new FormBody.Builder().add("username", "flyboy")
                .add("password", "086319").build();

        Request request = new Request.Builder().url("https://www.wanandroid.com/user/login")
                .post(formBody).build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 已保存登录状态cookie,发送需要cookie的请求接口
        request = new Request.Builder().url("https://www.wanandroid.com/lg/collect/list/0/json").build();

        call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private final  Map<String, List<Cookie>> cookieStore= new HashMap<>();
    public void cookieExtra()
    {
        /*
            因为okhttp只提供cookie处理方法，不提供保存方法，所以需要自己保存此处我们保存至手机本地内存中
         */

        Request request = new Request.Builder()
                .url("uri")
                .header("Cookie", "xxx")
                .build();

        /*
           然后可以从返回的response里得到新的Cookie，你可能得想办法把Cookie保存起来。
           但是OkHttp可以不用我们管理Cookie，自动携带，保存和更新Cookie。
           方法是在创建OkHttpClient设置管理Cookie的CookieJar
         */

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
                        cookieStore.clear();
                        cookieStore.put(httpUrl.host(),list);
                    }

                    @NonNull
                    @Override
                    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                        List<Cookie> list = cookieStore.get(httpUrl.host());
                        return list != null ? list : new ArrayList<>();
                    }
                })
                .build();

        /*
           也可以将服务器返回的Cookie保存中application中全局共享
         */
    }

    private void postAsyncHttp() {
        OkHttpClient mOkHttpClient=new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {

                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                        cookieStore.put(HttpUrl.parse("http://192.168.31.231:8080/shiro-2"), cookies);
                        for(Cookie cookie:cookies){
                            System.out.println("cookie Name:"+cookie.name());
                            System.out.println("cookie Path:"+cookie.path());
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse("http://192.168.31.231:8080/shiro-2"));
                        if(cookies==null){
                            System.out.println("没加载到cookie");
                        }
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();

        RequestBody formBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "admin")
                .build();

        final Request request = new Request.Builder()
                .url("http://192.168.31.231:8080/shiro-2/shiro-login")
                .post(formBody)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("test", str);

                Log.d("test", "请求成功!");
            }
        });
    }
}
