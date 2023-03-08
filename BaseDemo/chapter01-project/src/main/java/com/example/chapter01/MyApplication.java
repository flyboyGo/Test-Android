package com.example.chapter01;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chapter01.database.ShoppingDBHelper;
import com.example.chapter01.enity.Goods;
import com.example.chapter01.util.FileUtil;
import com.example.chapter01.util.SharedUtil;

import java.io.File;
import java.util.List;

public class MyApplication extends Application {

    /*
         适合在Application中保存的全局变量主要有下面3类数据：
         1、会频繁读取的信息，如用户名、手机号等。
         2、不方便由意图传递的数据，例如位图对象、非字符串类型的集合对象等。
         3、容易因频繁分配内存而导致内存泄漏的对象，如Handler对象等。
     */

    // 单例模式
    private  static  MyApplication myApplication;

    // 全局变量，购物车中的商品总数量
    public int goodsCount;

    public static MyApplication getInstance(){
        return myApplication;
    }


    // 在App启动前调用
    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
        Log.d("test","onCreate");

        // 初始化商品信息
        initGoodsInfo();
    }

    // 在App重止时调用
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void initGoodsInfo() {
        // 获取共享参数、判断是否是第一次打开
        boolean isFirst = SharedUtil.getInstance(this).readBoolean("first", true);
        // 拼接路径
        String directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator;
        if(isFirst){
            // 模拟网络图片下载
            List<Goods> list = Goods.getDefaultList();
            for(Goods goods : list)
            {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), goods.pic);
                // 完整路径
                String path = directory + goods.id + ".jpg";
                // 往存储卡保存商品图片
                FileUtil.saveImage(path,bitmap);
                // 回收位图对象
                bitmap.recycle();
                goods.picPath = path;
            }

            //打开数据库，把商品信息插入到表中
            ShoppingDBHelper shoppingDBHelper = ShoppingDBHelper.getInstance(this);
            shoppingDBHelper.openWriteLink();
            shoppingDBHelper.insertGoods(list);
            shoppingDBHelper.closeLink();

            // 是否是第一次打开，写入共享参数
            SharedUtil.getInstance(this).writeBoolean("first",true);
        }
    }

    // 在配置改变时调用，例如横屏变为竖屏
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("test","onConfigurationChanged");
    }
}
