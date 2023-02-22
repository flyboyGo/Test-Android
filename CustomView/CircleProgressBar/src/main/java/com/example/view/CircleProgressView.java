package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleProgressView extends View {

    // 进度条的范围
    private int current = 0,max = 100;
    // 圆弧(圆环)的宽度
    private float arcWidth = 0;
    // 控件的宽度
    private float width;
    // 画笔
    private Paint outerCirclePaint;
    private Paint innerCirclePaint;
    private Paint progressPaint;
    private Paint circlePaint;
    private Paint contentPaint;
    private Paint buttonPaint;
    private Paint startInfoPaint;
    private Paint stopInfoPaint;
    // 优化状态
    boolean isRunning = false;
    private float downX;
    private float downY;
    private Paint pointPaint;

    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initPaints();

        initAttrs();
    }

    // 初始化属性
    private void initAttrs() {

    }

    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // getMeasuredWidth获取的是view的原始大小，也就是xml中配置或者代码中设置的大小
        // getWidth获取的是view最终显示的大小，这个大小不一定等于原始大小
        width = getMeasuredWidth();
        initButtonClickRange();
    }

    // 布局
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    // 绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOuterCircle(canvas);

        drawInnerCircle(canvas);

        drawProgress(canvas);

        drawPoint(canvas);

        drawCircle(canvas);

        drawTitle(canvas);

        drawTime(canvas);

        drawButton(canvas);

        drawStartInfo(canvas);

        drawStopInfo(canvas);
    }

    private void initPaints(){

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(getResources().getColor(R.color.blue, getContext().getTheme()));
        outerCirclePaint.setStyle(Paint.Style.STROKE);
        outerCirclePaint.setStrokeWidth(15f);
        outerCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        outerCirclePaint.setAntiAlias(true);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(getResources().getColor(R.color.blue, getContext().getTheme()));
        innerCirclePaint.setStyle(Paint.Style.STROKE);
        innerCirclePaint.setStrokeWidth(8f);
        innerCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        innerCirclePaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setColor(getResources().getColor((R.color.yellow), getContext().getTheme()));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(getResources().getColor(R.color.white,getContext().getTheme()));
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setStrokeWidth(1f);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);
        pointPaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setColor(getResources().getColor(R.color.blue, getContext().getTheme()));
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        contentPaint = new Paint();
        contentPaint.setColor(getResources().getColor(R.color.white,getContext().getTheme()));
        contentPaint.setStyle(Paint.Style.FILL);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        contentPaint.setAntiAlias(true);

        buttonPaint = new Paint();
        buttonPaint.setColor(getResources().getColor(R.color.white, getContext().getTheme()));
        buttonPaint.setStyle(Paint.Style.FILL);
        buttonPaint.setStrokeCap(Paint.Cap.ROUND);
        buttonPaint.setAntiAlias(true);

        startInfoPaint = new Paint();
        startInfoPaint.setColor(getResources().getColor(R.color.blue,getContext().getTheme()));
        startInfoPaint.setStyle(Paint.Style.FILL);
        startInfoPaint.setStrokeCap(Paint.Cap.ROUND);
        startInfoPaint.setAntiAlias(true);

        stopInfoPaint = new Paint();
        stopInfoPaint.setColor(getResources().getColor(R.color.white,getContext().getTheme()));
        stopInfoPaint.setStyle(Paint.Style.FILL);
        stopInfoPaint.setStrokeCap(Paint.Cap.ROUND);
        stopInfoPaint.setAntiAlias(true);
    }

    private void drawOuterCircle(Canvas canvas){
        // 外圆环
        int radius = (int)(width / 2f);
        canvas.save();
        canvas.drawCircle(radius,radius,radius * 0.9f,outerCirclePaint);
        canvas.restore();
    }

    private void drawInnerCircle(Canvas canvas){
        // 内圆环
        int radius = (int)(width / 2f);
        canvas.save();
        canvas.drawCircle(radius,radius,radius * 0.7f,innerCirclePaint);
        canvas.restore();
    }

    private void drawProgress(Canvas canvas){
        // 进度弧线
        int radius = (int)(width / 2f);
        arcWidth = (int)((width / 2) * 0.17);
        progressPaint.setStrokeWidth(arcWidth);
        RectF rectF = new RectF();
        rectF.set(radius * 0.2f,radius * 0.2f,(float) (radius * 2 - radius * 0.2),(float) (radius * 2 - radius * 0.2));
        // 渐变
        progressPaint.setShader(new LinearGradient(radius * 0.2f,radius * 0.2f,(float) (radius * 2 - radius * 0.2),(float) (radius * 2 - radius * 0.2),
                getResources().getColor(R.color.gradientFirst, getContext().getTheme()),
                getResources().getColor(R.color.gradientSecond, getContext().getTheme()),
                Shader.TileMode.CLAMP));
        canvas.save();
        canvas.drawArc(rectF,-90,(float) (current * 360 / max),false, progressPaint);
        canvas.restore();
    }

    private void drawPoint(Canvas canvas){
        // 进度弧线
        int radius = (int)(width / 2f);
        canvas.save();
        canvas.drawCircle(radius ,radius * 0.2f,radius * 0.05f,pointPaint);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas){
        // 内实心圆
        int radius = (int)(width / 2f);
        canvas.save();
        canvas.drawCircle(radius,radius,radius * 0.67f,circlePaint);
        canvas.restore();
    }

    private void drawTitle(Canvas canvas) {
        int radius = (int)(width / 2f);
        contentPaint.setTextSize(SizeUtil.dip2px(23));
        canvas.save();
        canvas.drawText("本次优化时间",radius * 0.6f,radius * 0.7f,contentPaint);
        canvas.restore();
    }

    private void drawTime(Canvas canvas) {
        int radius = (int)(width / 2f);
        contentPaint.setTextSize(SizeUtil.dip2px(35));
        canvas.save();
        canvas.drawText("00:00",radius * 0.75f,radius,contentPaint);
        canvas.restore();
    }

    private void drawButton(Canvas canvas){
        int radius = (int)(width / 2f);
        RectF rectF = new RectF();
        rectF.set(radius * 0.6f, radius * 1.1f, radius  * 1.4f,radius * 1.35f);
        canvas.save();
        canvas.drawRoundRect(rectF,radius * 0.2f, radius * 0.2f, buttonPaint);
        canvas.restore();
    }

    private void drawStartInfo(Canvas canvas){
        int radius = (int)(width / 2f);
        startInfoPaint.setTextSize(SizeUtil.dip2px(20));
        canvas.save();
        canvas.drawText("开始优化",radius * 0.78f,radius* 1.27f,startInfoPaint);
        canvas.restore();
    }

    private void drawStopInfo(Canvas canvas){
        int radius = (int)(width / 2f);
        stopInfoPaint.setTextSize(SizeUtil.dip2px(15));
        canvas.save();
        canvas.drawText("结束优化",radius * 0.85f,radius* 1.5f,stopInfoPaint);
        canvas.restore();
    }

    // 按钮的点击范围
    float startFrontX,startEndX,startFrontY,startEndY;
    float stopFrontX,stopEndX,stopFrontY,stopEndY;

    private void initButtonClickRange(){
        float radius = width / 2f;
        startFrontX = radius * 0.6f;
        startEndX = radius * 1.4f;
        startFrontY = radius * 1.1f;
        startEndY = radius * 1.35f;

        stopFrontX = radius * 0.7f;
        stopEndX = radius * 1.3f;
        stopFrontY = radius * 1.36f;
        stopEndY = radius * 1.5f;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        downX = event.getX();
        downY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(downX >=startFrontX && downX <= startEndX && downY >= startFrontY && downY <= startEndY){
                    Log.d("test","开始优化");
                }
                else if(downX >=stopFrontX && downX <= stopEndX && downY >= stopFrontY && downY <= stopEndY){
                    Log.d("test","结束优化");
                }
                break;
        }
        return false;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
