package com.example.jetpack.base.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLocationObserver implements LifecycleObserver {

    private Context context;
    private LocationManager locationManager;
    private MyLocationListener myLocationListener;

    public MyLocationObserver(Context context) {
        this.context = context;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void startGetLocation() {

        Log.d("test", "startGetLocation");

        // 获取系统中位置服务
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 检查动态权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // 实例化位置监听对象
        myLocationListener = new MyLocationListener();
        // 添加监听对象
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, myLocationListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void stopGetLocation()
    {
        Log.d("test","stopGetLocation");
        // 注销位置监听器
        locationManager.removeUpdates(myLocationListener);
    }

    // 自定义位置监听类(事件发生时,调用对象的方法,类似于回调函数)
    static class MyLocationListener implements LocationListener
    {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            Log.d("test","location changed == "+location.toString());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
        /**
         * adb -s emulator-5554 emu geo fix 121.4961236714487 31.24010934431376
         * adb -s emulator-5554 emu geo fix 122.4961236714487 31.24010934431376
         */
    }

}
