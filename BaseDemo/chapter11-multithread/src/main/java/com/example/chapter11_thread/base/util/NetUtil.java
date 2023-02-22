package com.example.chapter11_thread.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class NetUtil {

    public static  String doGet()
    {
        String result = "";
        BufferedReader reader = null;
        HttpURLConnection httpURLConnection = null;

        try {
            // 1、建立连接
            httpURLConnection = null;
            String url = "https://www.baidu.com";
            URL requestUrl = new URL(url);

            httpURLConnection = (HttpURLConnection)requestUrl.openConnection();
            // 设置请求的方式,默认为GET
            httpURLConnection.setRequestMethod("GET");
            // 设置连接超时的时间
            httpURLConnection.setConnectTimeout(5000);
            // 设置可以从连接中读取数据,默认是true
            httpURLConnection.setDoInput(true);
            // 开始连接
            httpURLConnection.connect();

            // 2、获取二进制流
            InputStream inputStream = httpURLConnection.getInputStream();

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

            result = builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null)
            {
                httpURLConnection.disconnect();
            }

            if(reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  result;
    }

    public static  boolean doPost(String urlStr)
    {
        HttpsURLConnection urlConnection = null;
        OutputStream outputStream = null;
        boolean result = false;

        try
        {
            URL url = new URL(urlStr);
            // 1、打开连接
            urlConnection = (HttpsURLConnection) url.openConnection();
            // 2、post的请求参数是放入Map中
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("username","lpf");
            paramMap.put("password","123456");
            String paramData = paramMapToString(paramMap);

            // 3、设置连接信息
            // 设置请求的方式,默认为GET
            urlConnection.setRequestMethod("POST");
            // 设置连接超时的时间
            urlConnection.setConnectTimeout(5000);
            // 设置内容长度
            urlConnection.setRequestProperty("Content_Length",String.valueOf(paramData.length()));
            // 设置conn可以向服务器端提交数据,默认是false
            urlConnection.setDoOutput(true);

            // 4、获取输出流,并进行数据
            outputStream = urlConnection.getOutputStream();
            outputStream.write(paramData.getBytes());

            // 5、获取服务器端的响应结果
            int code = urlConnection.getResponseCode();
            if(code == 200)
            {
                result = true;
            }

            // 开始连接
            // urlConnection.connect();

        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }

            if(outputStream != null)
            {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  result;
    }

    // 将Map转换为(utf-8)字符串形式
    public static String paramMapToString(Map<String, String> paramMap) {
        StringBuilder sb = new StringBuilder();
        try {
            Set<Map.Entry<String, String>> entries = paramMap.entrySet();
            for (Map.Entry<String, String> entry :
                    entries) {
                sb.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
            }
            // 去掉最后一个 &
            sb.deleteCharAt(sb.length() - 1);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
