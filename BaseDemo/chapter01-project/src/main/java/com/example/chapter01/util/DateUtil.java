package com.example.chapter01.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static  String getNowTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm::ss");
        return sdf.format(new Date());
    }

    public static  String getDate(Calendar calendar)
    {
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static  String getMonth(Calendar calendar)
    {
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(date);
    }

}
