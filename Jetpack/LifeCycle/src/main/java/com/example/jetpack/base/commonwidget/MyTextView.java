package com.example.jetpack.base.commonwidget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyTextView extends androidx.appcompat.widget.AppCompatTextView implements LifecycleObserver {

    public MyTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void showContent()
    {
        setText("显示数据");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void hideContent()
    {
        setText("隐藏数据");
    }
}
