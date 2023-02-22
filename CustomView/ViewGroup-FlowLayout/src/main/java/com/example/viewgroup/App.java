package com.example.viewgroup;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext(){
        return context;
    }
}
