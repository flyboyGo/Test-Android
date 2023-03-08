package com.example.chapter12_network_communication.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.chapter12_network_communication.R;

public class GlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        ImageView imageView = findViewById(R.id.image_view);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.muxing)
                .error(R.drawable.muxing)
                .fallback(R.drawable.muxing)
                .override(100,100);  //override指定加载图片大小

        DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

//        Glide.with(this)
//                .load(R.drawable.muxing)
//                .apply(requestOptions)
//                .transition(DrawableTransitionOptions.withCrossFade(factory))
//                .transform(new CircleCrop())
//                .into(imageView);

        // 使用GlideApp的模块注解可以直接流式操作
//        GlideApp.with(this)
//                .load(R.drawable.muxing)
//                .placeholder(R.drawable.muxing)
//                .error(R.drawable.muxing)
//                .fallback(R.drawable.muxing)
//                .override(100,100)
//                .transform(new CircleCrop())
//                .into(imageView);


        // 使用GlideApp的扩展注解,可以减少占位符重复代码的书写
        GlideApp.with(this)
                .load(R.drawable.muxing)
                .defaultImg()
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .into(imageView);

    }
}