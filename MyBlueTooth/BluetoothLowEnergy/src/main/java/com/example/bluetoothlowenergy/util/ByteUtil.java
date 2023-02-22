package com.example.bluetoothlowenergy.util;

public class ByteUtil {

    //获取高四位
    public static int getHeight4(byte data){
        int height;
        // >>> 无符号右移, >> 算术右移, << 算术左移
        height = ((data & 0xf0) >>> 4);
        return height;
    }

    //获取低四位
    public static int getLow4(byte data){
        int low;
        low = (data & 0x0f);
        return low;
    }
}
