package com.example.view_watch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class WatchView extends View {


    private int secondColor;
    private int minuteColor;
    private int hourColor;
    private int scaleColor;
    private boolean scaleShow;
    private int bgResId;
    private Bitmap bitmapBg = null;
    private Paint secondPaint;
    private Paint minutePaint;
    private Paint hourPaint;
    private Paint scalePaint;
    private int width;
    private int height;
    private Rect srcRect;
    private Rect srcDes;
    private Calendar calendar;

    public WatchView(Context context) {
        this(context,null);
    }

    public WatchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public WatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initAttrs(context,attrs);

        initPaints();

        initCalender();
    }

    private void initCalender() {
        // 获取日历、设置时区
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
    }

    // 获取属性、初始化属性
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Watch);
        secondColor = array.getColor(R.styleable.Watch_secondColor, getResources().getColor(R.color.secondDefaultColor, context.getTheme()));
        minuteColor = array.getColor(R.styleable.Watch_minuteColor, getResources().getColor(R.color.minuteDefaultColor, context.getTheme()));
        hourColor = array.getColor(R.styleable.Watch_hourColor, getResources().getColor(R.color.hourDefaultColor, context.getTheme()));
        scaleColor = array.getColor(R.styleable.Watch_scaleColor, getResources().getColor(R.color.scaleDefaultColor, context.getTheme()));
        bgResId = array.getResourceId(R.styleable.Watch_watchFaceBackground, -1);
        scaleShow = array.getBoolean(R.styleable.Watch_scaleShow,true);
        array.recycle();
        if(bgResId != -1){
            bitmapBg = BitmapFactory.decodeResource(getResources(), bgResId);
        }
    }

    // 测量自己
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getMode(heightMeasureSpec);
        // 减去外边距
        int widthTargetSize = widthSize - getPaddingLeft() - getPaddingRight();
        int heightTargetSize = heightSize - getPaddingTop() - getPaddingBottom();
        // 判断大小,取较小值
        int targetSize = Math.min(widthTargetSize, heightTargetSize);
        setMeasuredDimension(targetSize,targetSize);

        initRect();
    }

    private void initPaints() {
        // 秒针
        secondPaint = new Paint();
        secondPaint.setColor(secondColor);
        secondPaint.setStyle(Paint.Style.STROKE);
        secondPaint.setStrokeWidth(5f);
        secondPaint.setStrokeCap(Paint.Cap.ROUND);
        secondPaint.setAntiAlias(true);
        // 分针
        minutePaint = new Paint();
        minutePaint.setColor(minuteColor);
        minutePaint.setStyle(Paint.Style.STROKE);
        minutePaint.setStrokeWidth(10f);
        minutePaint.setStrokeCap(Paint.Cap.ROUND);
        minutePaint.setAntiAlias(true);
        // 时针
        hourPaint = new Paint();
        hourPaint.setColor(hourColor);
        hourPaint.setStyle(Paint.Style.STROKE);
        hourPaint.setStrokeWidth(15f);
        hourPaint.setStrokeCap(Paint.Cap.ROUND);
        hourPaint.setAntiAlias(true);
        // 刻度
        scalePaint = new Paint();
        scalePaint.setColor(scaleColor);
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setStrokeWidth(5f);
        scalePaint.setAntiAlias(true);
    }

    private void initRect() {
        // 注意:测量完，才可以获取大小
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        if(bitmapBg == null){
            Log.d("test","bitmapBg is null ....");
            return;
        }
        // 源坑 --> 从图片中截取,如果与图片大小一致,那么截取图片中所有的内容
        srcRect = new Rect();
        srcRect.left = 0;
        srcRect.top = 0;
        srcRect.right = bitmapBg.getWidth();
        srcRect.bottom = bitmapBg.getHeight();
        // 目标坑 --> 要填放源内容的位置
        srcDes = new Rect();
        srcDes.left = 0;
        srcDes.top = 0;
        srcDes.right = width;
        srcDes.bottom = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 每次绘制，更新时间
        long currentTimeMillis = System.currentTimeMillis();
        calendar.setTimeInMillis(currentTimeMillis);

        drawBackground(canvas);

        // drawBitmap(canvas);

        drawOuterCircle(canvas);

        // drawInnerCircle(canvas);

        drawScalePlus(canvas);

        int secondValue = calendar.get(Calendar.SECOND);

        if(secondValue == 0){
            drawSecondHand(canvas);

            drawMinuteHand(canvas);

            drawHourHand(canvas);
        }else{
            drawHourHand(canvas);

            drawMinuteHand(canvas);

            drawSecondHand(canvas);
        }

    }

    private void drawBackground(Canvas canvas){
        canvas.drawColor(Color.parseColor("#000000"));
    }

    private void drawBitmap(Canvas canvas){
        canvas.drawBitmap(bitmapBg,srcRect,srcDes,scalePaint);
    }

    private void drawOuterCircle(Canvas canvas){
        // 半径
        int radius = (int)(width / 2f);
        // 内环半径
        int innerC = (int)(width / 2 * 0.8f);
        // 外环半径
        int outerC = (int)(width / 2 * 0.9f);
        canvas.save();
        canvas.drawCircle(radius,radius,radius,scalePaint);
        canvas.restore();
    }

    private void drawInnerCircle(Canvas canvas){
        // 半径
        int radius = (int)(width / 2f);
        // 内环半径
        int innerC = (int)(width / 2 * 0.8f);
        // 外环半径
        int outerC = (int)(width / 2 * 0.9f);
    }

    private void drawScale(Canvas canvas){
        // 半径
        int radius = (int)(width / 2f);
        // 内环半径
        int innerC = (int)(width / 2 * 0.85f);
        // 外环半径
        int outerC = (int)(width / 2 * 0.95f);
        canvas.drawCircle(radius,radius,SizeUtil.dip2px(5),scalePaint);

        for(int i = 0; i < 12; i++){
            // 等份的角度
            double th = Math.PI * 2 / 12 * i;
            // 内环
            int innerB = (int)(Math.cos(th) * innerC);
            int innerY = height / 2 - innerB;
            int innerA = (int)(innerC * Math.sin(th));
            int innerX = width / 2 + innerA;
            // 外环
            int outerB = (int)(Math.cos(th) * outerC);
            int outerY = height / 2 - outerB;
            int outerA = (int)(outerC * Math.sin(th));
            int outerX = width / 2 + outerA;
            canvas.drawLine(innerX,innerY,outerX,outerY,scalePaint);
        }
    }

    private void drawScalePlus(Canvas canvas){
        // 半径
        int radius = (int)(width / 2f);
        // 内环半径
        int innerC = (int)(width / 2 * 0.85f);
        // 外环半径
        int outerC = (int)(width / 2 * 0.95f);
        canvas.save();
        canvas.drawCircle(radius,radius,SizeUtil.dip2px(5),scalePaint);
        for(int i = 0; i < 12; i++){
            canvas.drawLine(radius,radius - outerC,radius,radius-innerC,scalePaint);
            canvas.rotate(30,radius,radius);
        }
        canvas.restore();
    }

    private void drawHourHand(Canvas canvas){
        // 半径
        int radius = (int)(width / 2f);
        // 时针的长度
        int hourRadius = (int)(radius * 0.8f);
        // 获取时间
        int hourValue = calendar.get(Calendar.HOUR);
        int minuteValue = calendar.get(Calendar.MINUTE);
        canvas.save();
        // 求旋转的角度
        float hourOffsetRotate = (float)minuteValue * 30 / 60;
        float hourRotate = hourValue * 30 + hourOffsetRotate;
        canvas.rotate(hourRotate,radius,radius);
        canvas.drawLine(radius,radius - hourRadius,radius,radius - SizeUtil.dip2px(5),hourPaint);
        canvas.restore();
    }

    private void drawMinuteHand(Canvas canvas){
        // 半径
        int radius = (int)(width / 2f);
        // 分针的长度
        int minuteRadius = (int)(radius * 0.75f);
        // 获取时间
        int minuteValue = calendar.get(Calendar.MINUTE);
        // 求旋转的角度
        float minuteRotate = (float) minuteValue  * 360 / 60;
        canvas.save();
        canvas.rotate(minuteRotate,radius,radius);
        canvas.drawLine(radius,radius - minuteRadius,radius,radius - SizeUtil.dip2px(5),minutePaint);
        canvas.restore();
    }

    private void drawSecondHand(Canvas canvas){
        // 半径
        int radius = (int)(width / 2f);
        // 分针的长度
        int secondRadius = (int)(radius * 0.8f);
        // 获取时间
        int secondValue = calendar.get(Calendar.SECOND);
        // 求旋转的角度
        float secondRotate = (float) secondValue  * 360 / 60;
        canvas.save();
        canvas.rotate(secondRotate,radius,radius);
        canvas.drawLine(radius,radius - secondRadius,radius,radius - SizeUtil.dip2px(5),secondPaint);
        canvas.restore();
    }

    private boolean isUpdate = false;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isUpdate = true;
        post(new Runnable() {
            @Override
            public void run() {
                if(isUpdate){
                    // 重新绘制UI
                    //postInvalidate();
                    invalidate();
                    postDelayed(this,1000);
                }else{
                    removeCallbacks(this);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isUpdate = false;
    }
}
