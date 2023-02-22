package com.example.chapter11_thread.view_pager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapTask extends AsyncTask<Integer,Void, Bitmap> {

    private int res;
    private ImageView imageView;
    private Context context;

    public BitmapTask(Context context,ImageView imageView)
    {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        res = params[0];

//        try {
//            Thread.sleep(2 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // 根据图片资源id,获取对应的Bitmap位图
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),res);

        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        // 显示记载成功的图片
        imageView.setImageBitmap(bitmap);
    }
}
