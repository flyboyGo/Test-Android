package com.example.chapter01.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {



    // 把字符串保存到指定路径的文本文件
    public static  void saveText(String path,String text)
    {
        BufferedWriter os = null;
        try {
            os = new BufferedWriter(new FileWriter(path));
            os.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(os != null)
            {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 从指定路径的文本文件读取内容
    public static String readText(String path)
    {
        BufferedReader is = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = new BufferedReader(new FileReader(path));
            String line = null;
            while ((line = is.readLine()) !=null)
            {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null)
            {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    // 把位图数据保存到指定路径的图片文件
    public static void saveImage(String path, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            // 把位图数据压缩到文件输入流中
            /*
               读取图片文件的花样倒是挺多，把位图数据写入图片文件却只有一种，
               即通过位图对象的compress方法将位图数据压缩到文件输出流
             */
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, fos);
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(fos != null)
            {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // 从指定路径的图片文件中读取位图数据
    public static Bitmap readImage(String path) {
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(fis);
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  bitmap;
    }
}
