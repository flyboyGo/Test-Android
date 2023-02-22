package com.example.chapter11_thread.base.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherUtil {
    public static String BASE_URL = "https://www.yiketianqi.com/free/day";
    public static String APP_ID = "36646344";
    public static String APP_SECRET = "c1lgQbP9";

    public static  String doGet(String url)
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {
            // 1、建立连接
            URL requestUrl = new URL(url);
            // 打开连接
            urlConnection = (HttpURLConnection)requestUrl.openConnection();
            // 设置请求的方式,默认为GET
            urlConnection.setRequestMethod("GET");
            // 设置连接超时的时间
            urlConnection.setConnectTimeout(5000);
            // 设置字符的格式
            urlConnection.setRequestProperty("Charset","utf-8");
            // 开始连接
            urlConnection.connect();

            // 2、获取二进制流
            InputStream inputStream = urlConnection.getInputStream();

            // 3、通过InputStreamReader转换流,将原始的二进制流变成字符流;再将字符流变成字符缓冲流
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // 4、从字符缓冲流中读取字符串
            StringBuilder builder = new StringBuilder();
            String line = "";
            while((line = reader.readLine()) != null)
            {
                builder.append(line);
                builder.append("\n");
            }

            if(builder.length() == 0)
            {
                return  null;
            }

            bookJSONString = builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  bookJSONString;
    }

    public static String getWeatherOfCity(String city)
    {
        // 拼接get请求的url
        String weatherUtl = BASE_URL+"?"+"appid="+APP_ID+"&appsecret="+APP_SECRET+"&city="+city+"&unescape=1";
        Log.d("test",weatherUtl);
        String weatherResult = doGet(weatherUtl);
        return weatherResult;
    }
}
