package com.example.view_imageviewplus;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImageViewPlus extends androidx.appcompat.widget.AppCompatImageView {

    boolean status;

    public ImageViewPlus(@NonNull Context context) {
        this(context,null);
    }

    public ImageViewPlus(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageViewPlus(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context,attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageViewPlus);
        status = array.getBoolean(R.styleable.ImageViewPlus_status, false);
        array.recycle();
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
