package com.example.combinedview_digitalkeyboard;

import android.app.Application;
import android.os.Handler;

public class App extends Application {

    private static Handler handler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    public static Handler getHandler(){
        return handler;
    }
}
