package com.example.viewgroup_digitalkeyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Printer;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class KeyPadView extends ViewGroup {

    public static final int DEFAULT_ROW = 4;
    public static final int DEFAULT_COLUMN = 3;
    public static final int DEFAULT_MARGIN = SizeUtil.dip2px(2);

    private int textColor;
    private float textSize;
    private int itemPressColor;
    private int itemNormalColor;
    private int row = DEFAULT_ROW;
    private int column = DEFAULT_COLUMN;
    private int itemMargin = DEFAULT_MARGIN;
    private OnKeyClickListener onKeyClickListener;

    public KeyPadView(Context context) {
        this(context,null);
    }

    public KeyPadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public KeyPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public KeyPadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        
        initAttrs(context,attrs);

        setUpItem();

        initView(context);

        initEvent();
    }

    private void initEvent() {
    }

    private void initView(Context context) {

    }

    // 添加子View
    private void setUpItem() {
        // 清空ViewGroup、再添加子View
        removeAllViews();

        for(int i = 0; i < 11; i++){
            TextView item = new TextView(getContext());
            if(i == 10){
                item.setTag(true);
                item.setText("删除");
            }else{
                item.setTag(false);
                item.setText(String.valueOf(i));
            }
            // 设置相关属性
            item.setGravity(Gravity.CENTER);
            item.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);// 默认为sp
            item.setTextColor(textColor);
            // 设置背景
            item.setBackground(providerItemBg());
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onKeyClickListener != null){
                        if(!(boolean)v.getTag()){
                            onKeyClickListener.onNumberClick(Integer.parseInt(((TextView)v).getText().toString()));
                        }
                        else{
                            onKeyClickListener.onDeleteClick();
                        }

                    }
                }
            });
            // 将生成的子View添加到ViewGroup中
            addView(item);
        }
    }

    // 生成item的selector的背景Drawable
    private Drawable providerItemBg(){
        // 创建bg的selector
        StateListDrawable bg = new StateListDrawable();
        // textView的按下的bg
        GradientDrawable pressDrawable = new GradientDrawable();
        pressDrawable.setColor(itemPressColor);
        // pressDrawable.setColor(getResources().getColor(R.color.key_item_press_color));
        pressDrawable.setCornerRadius(SizeUtil.dip2px(5));
        bg.addState(new int[]{android.R.attr.state_pressed},pressDrawable);
        // textView的普通的bg
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setColor(itemNormalColor);
        // normalDrawable.setColor(getResources().getColor(R.color.key_item_color,getContext().getTheme()));
        normalDrawable.setCornerRadius(SizeUtil.dip2px(5));
        bg.addState(new int[]{},normalDrawable);
        return bg;
    }

    // 获取、初始化属性
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.KeyPadView);
        textColor = array.getColor(R.styleable.KeyPadView_numberColor, context.getResources().getColor(R.color.white));
        // 返回的是px单位(相同sp单位大小,在不同的屏幕下会生成不同的px,sp会自动适配)
        textSize = array.getDimensionPixelSize(R.styleable.KeyPadView_numberSize, -1);
        itemPressColor = array.getColor(R.styleable.KeyPadView_itemPressColor, context.getResources().getColor(R.color.key_item_press_color));
        itemNormalColor = array.getColor(R.styleable.KeyPadView_itemNormalColor, context.getResources().getColor(R.color.key_item_color));
        itemMargin = array.getDimensionPixelSize(R.styleable.KeyPadView_itemMargin, DEFAULT_MARGIN);
        array.recycle();
    }

    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int verticalPadding = this.getPaddingBottom() + this.getPaddingTop();
        int horizontalPadding = this.getPaddingLeft() + this.getPaddingRight();

        // getSize() the size in pixels defined in the supplied measure specification
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // 一行三列,三等分,求出列宽
        int perItemWidth = (widthSize-(column + 1) * itemMargin ) / column;
        int perItemHeight = (heightSize-(row + 1) * itemMargin )  / row;
        // 创建子View的宽高、模式
        int normalWidthSpec = MeasureSpec.makeMeasureSpec(perItemWidth, MeasureSpec.EXACTLY);
        int specialWidthSpec = MeasureSpec.makeMeasureSpec(perItemWidth * 2 + itemMargin,MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(perItemHeight, MeasureSpec.EXACTLY);

        // 测量孩子
        for(int i = 0; i < getChildCount(); i++){
            View item = getChildAt(i);
            boolean isDelete = (boolean)item.getTag();
            item.measure(isDelete ? specialWidthSpec : normalWidthSpec, heightSpec);
        }
        // 测量自己
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    // 布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int leftPadding = this.getLeftPaddingOffset();
        int paddingTop = this.getPaddingTop();

        int childCount = this.getChildCount();
        int left = 0,top = 0,right = 0,bottom = 0;
        for (int i = 0; i < childCount; i++) {
            // 求出当前元素在第几行、第几列
            int rowIndex = i / column;
            int columnIndex = i % column;
            // 获取子View
            View item = this.getChildAt(i);
            // 迭代布局位置
            if(i == 10){
                left = getChildAt(9).getMeasuredWidth() + columnIndex * itemMargin + itemMargin;
            }else{
                left = columnIndex * item.getMeasuredWidth() + columnIndex * itemMargin + itemMargin;
            }
            right = left + item.getMeasuredWidth();

            top = rowIndex * item.getMeasuredHeight() + rowIndex * itemMargin + itemMargin;
            bottom = top + item.getMeasuredHeight();

            // 布局子View
            item.layout(left,top,right,bottom);
        }
    }

    // 定义接口
    public void setOnKeyClickListener(OnKeyClickListener onKeyClickListener){
        this.onKeyClickListener = onKeyClickListener;
    }
    public interface  OnKeyClickListener{
        void onNumberClick(int value);
        void onDeleteClick();
    }


    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getItemPressColor() {
        return itemPressColor;
    }

    public void setItemPressColor(int itemPressColor) {
        this.itemPressColor = itemPressColor;
    }

    public int getItemNormalColor() {
        return itemNormalColor;
    }

    public void setItemNormalColor(int itemNormalColor) {
        this.itemNormalColor = itemNormalColor;
    }

    public int getItemMargin() {
        return itemMargin;
    }

    public void setItemMargin(int itemMargin) {
        this.itemMargin = itemMargin;
    }
}
