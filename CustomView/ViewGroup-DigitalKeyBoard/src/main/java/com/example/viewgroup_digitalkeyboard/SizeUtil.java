package com.example.viewgroup_digitalkeyboard;

public class SizeUtil {

    public static int dip2px(float dpValue){
        float scale = App.getAppContext().getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
}
