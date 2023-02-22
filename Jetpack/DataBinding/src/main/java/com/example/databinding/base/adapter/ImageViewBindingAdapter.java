package com.example.databinding.base.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.databinding.R;
import com.squareup.picasso.Picasso;

public class ImageViewBindingAdapter {

    // 加载网络图片
    @BindingAdapter("image")
    public static void setNetworkImage(ImageView imageView,String url)
    {
        if(!TextUtils.isEmpty(url))
        {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView);
        }
        else
        {
            imageView.setBackgroundColor(Color.GRAY);
        }
    }

    // 加载本地图片
    @BindingAdapter("image")
    public static void setLocalImage(ImageView imageView,int resId)
    {
        imageView.setImageResource(resId);
    }

    // 多级加载图片资源
    @BindingAdapter(value = {"image","defaultImageResource"}, requireAll = false)
    public static void setImage(ImageView imageView,String url,int resId)
    {
        if(!TextUtils.isEmpty(url))
        {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
        }
        else
        {
            imageView.setImageResource(resId);
        }
    }

    // 加载本地图片
    @BindingAdapter("itemImage")
    public static void setLocalItemImage(ImageView imageView,int resId)
    {
        imageView.setImageResource(resId);
    }
}
