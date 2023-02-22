package com.example.chapter11_thread.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter11_thread.R;
import com.example.chapter11_thread.base.enity.Weather;
import com.example.chapter11_thread.base.util.WeatherUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class SimpleHandlerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_show;

    // 定义一个主线程的handler
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            // 判断是否是我们发送的消息
            if(msg.what == 0)
            {
                Toast.makeText(SimpleHandlerActivity.this,"已收到子线程的消息",Toast.LENGTH_SHORT).show();
                // 获取子线程发送的消息
                String jsonString = (String)msg.obj;
                // tv_show.setText(string);
                parseJsonDataAndShow(jsonString);
            }
        }
    };

    /*
       {
           "nums":226, //今日实时请求次数
           "cityid":"101120101", //城市ID
           "city":"济南",
           "date":"2022-05-05",
           "week":"星期四",
           "update_time":"22:38", //更新时间
           "wea":"多云", //天气情况
           "wea_img":"yun", //天气标识
           "tem":"25", //实况温度
           "tem_day":"30", //白天温度(高温)
           "tem_night":"23", //夜间温度(低温)
           "win":"南风", //风向
           "win_speed":"3级", //风力
           "win_meter":"19km\/h", //风速
           "air":"53", //空气质量
           "pressure":"987", //气压
           "humidity":"27%" //湿度
       }
     */

    public void parseJsonDataAndShow(String jsonStr)
    {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            String city = jsonObject.optString("city");
            String date = jsonObject.optString("date");
            String week = jsonObject.optString("week");
            String wea = jsonObject.optString("wea");
            String tem = jsonObject.optString("tem");
            String win = jsonObject.optString("win");
            String win_speed = jsonObject.optString("win_speed");
            String air = jsonObject.optString("air");
            String pressure = jsonObject.optString("pressure");
            String humidity = jsonObject.optString("humidity");

            // 显示json数据
            TextView tv_city = findViewById(R.id.city);
            tv_city.setText(city);
            TextView tv_date = findViewById(R.id.date);
            tv_date.setText(date);
            TextView tv_week = findViewById(R.id.week);
            tv_week.setText(week);
            TextView tv_wea= findViewById(R.id.wea);
            tv_wea.setText(wea);
            TextView tv_tem= findViewById(R.id.tem);
            tv_tem.setText(tem);
            TextView tv_win= findViewById(R.id.win);
            tv_win.setText(win);
            TextView tv_speed= findViewById(R.id.win_speed);
            tv_speed.setText(win_speed);
            TextView tv_air= findViewById(R.id.air);
            tv_air.setText(air);
            TextView tv_pressure= findViewById(R.id.pressure);
            tv_pressure.setText(pressure);
            TextView tv_humidity= findViewById(R.id.humidity);
            tv_humidity.setText(humidity);

            // 使用JSON库,直接解析json字符串,填充bean
            // 注意：bean中的属性名要与json字符串中的键名是一致的
            // 也可以通过使用@SerializedName("**")注解，实现一一对应
            Gson gson = new Gson();
            Weather weatherBean = gson.fromJson(jsonStr, Weather.class);
            Toast.makeText(this,weatherBean.toString(),Toast.LENGTH_SHORT).show();

            // 将javaBean对象转化为json字符串
            String jsonWeather = gson.toJson(weatherBean);
            Toast.makeText(this,jsonWeather,Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_handler);

        findViewById(R.id.btn_start).setOnClickListener(this);
        tv_show = findViewById(R.id.tv_show);
    }

    @Override
    public void onClick(View v)
    {
        start();
        Toast.makeText(this, "主体任务已结束", Toast.LENGTH_SHORT).show();
    }

    private void start()
    {
        // 一个耗时的任务
        new Thread(new Runnable() {
            @Override
            public void run() {

                /*
                StringBuilder string = new StringBuilder();
                int i = 1;
                for(; i <= 100; i++)
                {
                    string.append(i);
                }

                try
                {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                 */

                // 请求网络数据
                // String string = NetUtil.doGet();

                // 请求网络的天气数据
                String string = WeatherUtil.getWeatherOfCity("苏州");

                Message message = new Message();
                // what通常用于区分是谁发的消息
                message.what = 0;
                message.obj = string;

                // 使用主线程的handler向主线程发送消息
                handler.sendMessage(message);
            }
        }).start();
    }
}