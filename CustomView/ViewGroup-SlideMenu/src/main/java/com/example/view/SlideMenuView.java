package com.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

public class SlideMenuView extends ViewGroup implements View.OnClickListener {

    private int function;
    private View contentView;
    private View actionView;
    private OnActionClickListener onActionClickListener;
    private TextView actionRead;
    private TextView actionDelete;
    private TextView actionTop;
    // 动态更改数据,实现控件实时滑动
    private int contentLeft = 0;
    private Scroller scroller;

    public SlideMenuView(Context context) {
        this(context,null);
    }

    public SlideMenuView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public SlideMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initAttrs(context,attrs);
    }

    // 获取属性、初始化属性
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlideMenuView);
        function = array.getInt(R.styleable.SlideMenuView_function, -1);
        array.recycle();
    }

    // 页面加载结束
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        // 判断是否只有一个子View
        if(childCount > 1){
            throw new IllegalArgumentException("only have one child");
        }

        // 获取第一个孩子
        contentView = getChildAt(0);

        // 添加第二个孩子
        // LayoutInflater.from(getContext()).inflate(R.layout.item_slide_action,this,true);
        // 子View没有attach to root,需要我们手动的添加
        actionView = LayoutInflater.from(getContext()).inflate(R.layout.item_slide_action, this, false);
        this.addView(actionView);

        initView();
    }

    private void initView() {
        scroller = new Scroller(getContext());
        actionRead = actionView.findViewById(R.id.action_read);
        actionDelete = actionView.findViewById(R.id.action_delete);
        actionTop = actionView.findViewById(R.id.action_top);

        actionRead.setOnClickListener(this);
        actionDelete.setOnClickListener(this);
        actionTop.setOnClickListener(this);
    }

    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int parentHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 注意: 获取孩子时,孩子是attach to root的情况,是无法直接获取子View,而是获取到root;
        // 需要我们手动addView()
        // 测量第一个孩子,内容部分,宽度与父类一致;
        // 高度三种情况,如果指定大小,获取参数,直接测量; 如果包裹内容,at_most; 如果match_parent,那就给他大小;

        LayoutParams contentLayoutParams = contentView.getLayoutParams();
        int contentHeight = contentLayoutParams.height;

        int contentHeightMeasureSpace = 0;
        if(contentHeight == LayoutParams.MATCH_PARENT)
        {
            contentHeightMeasureSpace = MeasureSpec.makeMeasureSpec(parentHeightSize,MeasureSpec.EXACTLY);
        }
        else if(contentHeight == LayoutParams.WRAP_CONTENT)
        {
            contentHeightMeasureSpace = MeasureSpec.makeMeasureSpec(parentHeightSize,MeasureSpec.AT_MOST);
        }
        else{
            contentHeightMeasureSpace = MeasureSpec.makeMeasureSpec(contentHeight,MeasureSpec.EXACTLY);
        }

        contentView.measure(widthMeasureSpec,contentHeightMeasureSpace);

        // 拿到内容部分的高度
        int contentMeasureHeight = contentView.getMeasuredHeight();

        // 测量第二个孩子,编辑部分;
        // 宽度:3/4, 高度与内容部分一致
        int actionWidthSize = parentWidthSize / 4 * 3;
        actionView.measure(MeasureSpec.makeMeasureSpec(actionWidthSize,MeasureSpec.EXACTLY),
                           MeasureSpec.makeMeasureSpec(contentMeasureHeight,MeasureSpec.EXACTLY));

        // 测量自己,宽度为前面的宽度总和,高度与内容一样高
        setMeasuredDimension(actionWidthSize + parentWidthSize,contentMeasureHeight);
    }

    // 布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 摆放内容部分
        int contentTop = 0;
        int contentRight = contentLeft + contentView.getMeasuredWidth();
        int contentBottom = contentTop + contentView.getMeasuredHeight();
        contentView.layout(contentLeft,contentTop,contentRight,contentBottom);
        // 摆放编辑部分
        int actionLeft = contentRight;
        int actionTop = contentTop;
        int actionRight = actionLeft + actionView.getMeasuredWidth();
        int actionBottom = actionTop + actionView.getMeasuredHeight();
        actionView.layout(actionLeft,actionTop,actionRight,actionBottom);
    }

    // 定义功能接口
    public interface OnActionClickListener{
        void onReadClick();

        void onTopClick();

        void onDeleteClick();
    }

    public void setOnActionClickListener(OnActionClickListener listener){
        this.onActionClickListener = listener;
    }

    // 点击事件
    @Override
    public void onClick(View v) {
        // 点击编辑部分的功能按钮后,应该关闭编辑部分
        close();
        if(onActionClickListener != null){
            switch (v.getId()){
                case R.id.action_read:
                    onActionClickListener.onReadClick();
                    break;
                case R.id.action_delete:
                    onActionClickListener.onDeleteClick();
                    break;
                case R.id.action_top:
                    onActionClickListener.onTopClick();
                    break;
            }
        }
        else{
            Log.d("test","OnActionClickListener is null");
            return;
        }
    }


    private float downX;
    private float downY;
    // 是否已经打开标识
    private boolean isOpen = false;
    // 滑动方向的枚举
    private  Direction direction = Direction.NONE;
    enum Direction{ LEFT, RIGHT, NONE }
    // duration 走完的actionView 3/4的距离所需的时间
    private int duration = 700;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                Log.d("test","ACTION_DOWN ... x == " + downX + ", y == " + downY);
                break;

            case MotionEvent.ACTION_MOVE:
                // The left edge of the displayed part of your view, in pixels.
                int scrollX = getScrollX();
                float moveX = event.getX();
                float moveY= event.getY();
                // 移动的差值
                int dx = (int)(moveX -downX);
                downX = moveX;
                downY = moveY;
                if(dx > 0){
                    direction = Direction.RIGHT;
                }else {
                    direction = Direction.LEFT;
                }
                // 方法一:
                //重新布局
                // contentLeft += dx;
                //requestLayout();

                // 方法二: 直接滑动屏幕
                // 判断边界值的安全
                int resultScrollX = -dx + scrollX;
                if(resultScrollX < 0){
                    scrollTo(0,0);
                }
                else if(resultScrollX > actionView.getMeasuredWidth()){
                    scrollTo(actionView.getMeasuredWidth(),0);
                } else{
                    scrollBy(-dx,0);
                }
                Log.d("test","ACTION_MOVE ... x == " + moveX + ", y == " + moveY);
                break;

            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                float upY = event.getY();
                // 处理释放后,是显示还是收缩回去
                // 两个关注点，是否已经打开、滑动的方向
                // 获取已经滑动的页面距离
                int hasBeenScrollX = getScrollX();
                int actionViewWidth = actionView.getMeasuredWidth();
                if(isOpen){
                    // 当前状态打开
                    if(direction == Direction.RIGHT){
                        //方向向右，如果小于3/4，那么关闭;否则打开
                        if(hasBeenScrollX <= actionViewWidth / 4 * 3){
                            close();
                        }
                        else{
                            open();
                        }
                    }else if(direction == Direction.LEFT){
                        // 方向向左,无论距离大小都关闭
                        open();
                    }

                }else{
                    // 当前状态关闭
                    if(direction == Direction.LEFT){
                        // 向左滑动,判断滑动的距离,如果大于编辑部分的1/4则打开
                        if(hasBeenScrollX > actionViewWidth / 4){
                            open();
                        }
                        else{
                            close();
                        }
                    }else if(direction == Direction.NONE){
                        // 向右滑动
                        close();
                    }
                }
                Log.d("test","ACTION_UP ... x == " + upX + ", y == " + upY);
                break;
        }

        boolean result = super.onTouchEvent(event);
        Log.d("test","result === > " + result);

        // 返回true,表示事件已处理
        return true;
    }

    private float interceptDownX;
    private float interceptDownY;
    // 触摸事件拦截器
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 选择拦截
        // 如果是横向滑动,拦截,否则不拦截,可以传递给子View
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                interceptDownX = ev.getX();
                interceptDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float interceptMoveX = ev.getX();
                float interceptMoveY = ev.getY();
                if(Math.abs(interceptMoveX - interceptDownX) > 0){
                    // 自身消费,不继续传递子View,相当于过滤拦截
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void open(){
        // 显示出来
        // scrollTo(actionView.getMeasuredWidth(),0);
        int dx = actionView.getMeasuredWidth() - getScrollX();
        int currentDuration = (int)(dx / (actionView.getMeasuredWidth() / 4f * 3) * duration);
        scroller.startScroll(getScrollX(),0,actionView.getMeasuredWidth() - getScrollX(),0,Math.abs(currentDuration));
        isOpen = true;

        // 调用computeScroll()
        invalidate();
    }

    public void close(){
        // 隐藏
        // scrollTo(0,0);
        int dx = -getScrollX();
        int currentDuration = (int)(dx / (actionView.getMeasuredWidth() / 4f * 3) * duration);
        scroller.startScroll(getScrollX(),0, dx,0,Math.abs(currentDuration));
        isOpen = false;

        // 调用computeScroll()
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            int currX = scroller.getCurrX();
            // 滑动到某个位置
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }


    public boolean isOpen() {
        return isOpen;
    }

}
