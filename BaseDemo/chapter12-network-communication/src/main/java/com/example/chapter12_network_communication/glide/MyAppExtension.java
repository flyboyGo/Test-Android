package com.example.chapter12_network_communication.glide;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.resource.bitmap.Rotate;
import com.bumptech.glide.request.BaseRequestOptions;
import com.example.chapter12_network_communication.R;

@GlideExtension
public class MyAppExtension {

    private MyAppExtension()
    {
    }

    @GlideOption
    public static BaseRequestOptions<?> defaultImg (BaseRequestOptions<?> options){
        return options
                .placeholder(R.drawable.muxing)
                .error(R.drawable.muxing)
                .fallback(R.drawable.muxing)
                .transform(new Rotate(90));
    }
}
