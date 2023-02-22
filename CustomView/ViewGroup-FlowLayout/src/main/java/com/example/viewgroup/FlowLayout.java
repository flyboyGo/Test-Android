package com.example.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    public static final int DEFAULT_LINE = -1;
    public static final int DEFAULT_TEXT_MAX_LENGTH = -1;
    // 需要转单位,目前是px不适配
    public static final int DEFAULT_HORIZONTAL_MARGIN = SizeUtil.dip2px(5f);
    public static final int DEFAULT_VERTICAL_MARGIN = SizeUtil.dip2px(5f);
    public static final int DEFAULT_BORDER_RADIUS = SizeUtil.dip2px(5f);

    private int maxLines;
    private float itemHorizontalMargin;
    private float itemVerticalMargin;
    private int textMaxLength;
    private int textColor;
    private int borderColor;
    private float borderRadius;
    // 数据集合
    private List<String> data = new ArrayList<>();
    // 每一记录集合的集合
    private List<List<View>> lines = new ArrayList<>();
    // 监听器
    private OnItemClickListener onItemClickListener;


    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initAttrs(context,attrs);

        initViews(context);

        initEvent();
    }

    // 设置数据集合
    public void setTextList(List<String> data){
        this.data.clear();
        this.data.addAll(data);
        // 根据数据创建子View,并且添加进来
        setUpChildren();
    }

    // 添加子View
    private void setUpChildren() {
        // 先清空原有的内容
        removeAllViews();
        // 添加子View进来
        for (String datum : data) {
            TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_flow_text,this,false);
            if(textMaxLength != -1){
                // 设置TextView的最大内容长度
                textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textMaxLength)});
            }
            // 设置TextView的相关属性
            textView.setText(datum);
            final String tempData = datum;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClickListener(v,tempData);
                    }
                }
            });
            addView(textView);
        }
    }

    // 自定义接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public  interface  OnItemClickListener{
        void  onItemClickListener(View v,String text);
    }

    /**
     * 这两个值来自于父控件,包含 模式(mode)(期望值,前2位)(不同的ViewGroup的期望值是不一样的) 和 值(size)(宽高,后30位)
     * int类型 ===> 4个字节 ==> 32位
     * 0 == > << 30  UNSPECIFIED (match_parent)
     * 1 ==>  << 30  EXACTLY  (width:xxx,height:xxx)
     * 2 ==>  << 30  AT_MOST  (wrap_content,有上限,父类的宽高)
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     *
     * 在测量、布局时,自定义ViewGroup的内边距需要我们注意,外边距则不需要
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int parentMode = MeasureSpec.getMode(widthMeasureSpec);
        int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        /**
         *         Log.d("test","parentMode == >" + parentMode);
         *         Log.d("test","parentHeightSize == > " + parentHeightSize);
         *         Log.d("test","parentWeightSize == > " + parentWidthSize);
         */

        int childCount = getChildCount();
        if(childCount == 0){
            return;
        }

        // 清空条目集合的集合
        lines.clear();
        // 添加默认行(一行条目为一个集合)
        List<View> line = new ArrayList<>();
        lines.add(line);

        // 构造子View需要的模式、值(宽高)
        int childWidthSpace = MeasureSpec.makeMeasureSpec(parentWidthSize, MeasureSpec.AT_MOST);
        int childHeightSpace = MeasureSpec.makeMeasureSpec(parentHeightSize,MeasureSpec.AT_MOST);

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if(child.getVisibility() != VISIBLE){
                continue;
            }
            // 测量孩子,
            measureChild(child,childWidthSpace,childHeightSpace);
            if(line.size() == 0){
                // 当前行为空,可以添加
                line.add(child);
            }else{
                // 判断是否可以添加到当前行
                boolean canBeAdd = checkChildCanBeAdd(line,child,parentWidthSize);
                if(!canBeAdd){
                    if(lines.size() >= maxLines && maxLines != -1){
                        // 行数大于等于最大行数,跳出循环
                        break;
                    }
                    // 当前行无法添加,新建行添加数据
                    line = new ArrayList<>();
                    lines.add(line);
                }
                line.add(child);
            }
        }
        // 根据尺寸计算所有行高,获取到总行高
        View child = getChildAt(0);
        int childHeight = child.getMeasuredHeight();
        int parentHeightTargetSize = childHeight * lines.size() + (lines.size() + 1) * (int)itemVerticalMargin + this.getPaddingTop() + this.getPaddingBottom();

        //  测量自己时不包含模式，必须是精确的值
         setMeasuredDimension(parentWidthSize, parentHeightTargetSize);
    }

    private boolean checkChildCanBeAdd(List<View> line,View child, int parentWidthSize) {
        // 当前需要添加子View的宽度
        int measuredWidth = child.getMeasuredWidth();
        int totalWidth = (int)itemHorizontalMargin + this.getPaddingLeft();
        for (View view : line) {
            totalWidth += view.getMeasuredWidth() + itemHorizontalMargin;
        }
        totalWidth += measuredWidth + itemHorizontalMargin + this.getPaddingRight();
        // 如果超出限制宽度,则不添加,否则可以添加
        return totalWidth <= parentWidthSize;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View firstChild = getChildAt(0);
        // 左、上、右、下
        int currentLeft = (int)itemHorizontalMargin + this.getPaddingLeft();
        int currentTop = (int)itemVerticalMargin + this.getPaddingTop();
        int currentRight = (int)itemHorizontalMargin + this.getPaddingRight();
        int currentBottom = firstChild.getMeasuredHeight() + (int)itemVerticalMargin + this.getPaddingBottom();

        for (List<View> line : lines) {
            for (View view : line) {
                // 布局一行
                int width = view.getMeasuredWidth();
                currentRight += width;
                // 判断最右边的边界
                if(currentRight > (this.getMeasuredWidth() - itemHorizontalMargin * 2)){
                    currentRight = this.getMeasuredWidth() - (int)itemHorizontalMargin * 2;
                }
                view.layout(currentLeft,currentTop,currentRight,currentBottom);
                currentLeft = currentRight + (int)itemHorizontalMargin;
                currentRight = currentRight + (int)itemHorizontalMargin;
            }
            currentLeft = (int)itemHorizontalMargin + this.getPaddingLeft();
            currentRight = (int)itemHorizontalMargin + this.getPaddingRight();
            currentTop += firstChild.getMeasuredHeight() + (int) itemVerticalMargin;
            currentBottom += firstChild.getMeasuredHeight() + (int) itemVerticalMargin;
        }
    }

    private void initEvent(){

    }

    private void initViews(Context context){

    }

    private void initAttrs(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        maxLines = typedArray.getInt(R.styleable.FlowLayout_maxLines, DEFAULT_LINE);
        if(maxLines < 1 && maxLines != DEFAULT_LINE){
            throw new IllegalArgumentException("maxLines can not less than one");
        }
        itemHorizontalMargin = typedArray.getDimension(R.styleable.FlowLayout_itemHorizontalMargin, DEFAULT_HORIZONTAL_MARGIN);
        itemVerticalMargin = typedArray.getDimension(R.styleable.FlowLayout_itemVerticalMargin, DEFAULT_VERTICAL_MARGIN);
        textMaxLength = typedArray.getInt(R.styleable.FlowLayout_textMaxLength, DEFAULT_TEXT_MAX_LENGTH);
        if(textMaxLength <= 0 && textMaxLength != DEFAULT_TEXT_MAX_LENGTH){
            throw new IllegalArgumentException("textMaxLength can not less than zero");
        }
        textColor = typedArray.getColor(R.styleable.FlowLayout_textColor, getResources().getColor(R.color.text_grey));
        borderColor = typedArray.getColor(R.styleable.FlowLayout_borderColor, getResources().getColor(R.color.text_grey));
        borderRadius = typedArray.getDimension(R.styleable.FlowLayout_borderRadius, DEFAULT_BORDER_RADIUS);
        Log.d("test","maxLines == > " + maxLines);
        Log.d("test","itemHorizontalMargin == > " + itemHorizontalMargin);
        Log.d("test","itemVerticalMargin == > " + itemVerticalMargin);
        Log.d("test","textMaxLength == > " + textMaxLength);
        Log.d("test","textColor == > " + textColor);
        Log.d("test","borderColor == > " + borderColor);
        Log.d("test","borderRadius == > " + borderRadius);
        typedArray.recycle();
    }

    // getter、setter方法
    public int getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    public Float getItemHorizontalMargin() {
        return itemHorizontalMargin;
    }

    public void setItemHorizontalMargin(Float itemHorizontalMargin) {
        this.itemHorizontalMargin = SizeUtil.dip2px(itemHorizontalMargin);
    }

    public Float getItemVerticalMargin() {
        return itemVerticalMargin;
    }

    public void setItemVerticalMargin(Float itemVerticalMargin) {
        this.itemVerticalMargin = SizeUtil.dip2px(itemVerticalMargin);
    }

    public int getTextMaxLength() {
        return textMaxLength;
    }

    public void setTextMaxLength(int textMaxLength) {
        this.textMaxLength = textMaxLength;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
    }
}
