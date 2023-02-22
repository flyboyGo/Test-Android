package com.example.chapter01.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {
    private static SharedUtil mUtil;
    private SharedPreferences preferences = null;

    public static SharedUtil getInstance(Context context)
    {
        if(mUtil == null)
        {
            mUtil = new SharedUtil();
            mUtil.preferences = context.getSharedPreferences("shopping",Context.MODE_PRIVATE);
        }
        return mUtil;
    }

    public void writeBoolean(String key,boolean value)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public boolean readBoolean(String key,boolean defaultValue)
    {
        return preferences.getBoolean(key,defaultValue);
    }
}
