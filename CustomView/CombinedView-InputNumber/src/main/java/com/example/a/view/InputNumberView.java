package com.example.a.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a.R;

public class InputNumberView extends RelativeLayout {

    private int currentNumber = 0;
    private TextView minusBtn;
    private TextView plusBtn;
    private EditText valueEdit;
    private OnNumberValueChangeListener onNumberChangeListener = null;
    private int max;
    private int min;
    private int step;
    private boolean disable;
    private int btnRightBackgroundRes;
    private int btnLeftBackgroundRes;
    private int defaultValue;

    // 以下四个构造函数重载,从上往下调用,保持入口统一
    public InputNumberView(Context context) {
        this(context,null);
    }

    public InputNumberView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InputNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public InputNumberView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // 定义属性(attrs.xml)
        // 获取相关属性
        //initAttrs(context, attrs);

        // 初始化各个子View布局,获取各个子View的对象
        initView(context);

        // 设置各个子View的监听事件
        setUpEvent();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.InputNumberView);
        max = typedArray.getInt(R.styleable.InputNumberView_max, 100);
        min = typedArray.getInt(R.styleable.InputNumberView_min, -100);
        step = typedArray.getInt(R.styleable.InputNumberView_step, 1);
        disable = typedArray.getBoolean(R.styleable.InputNumberView_disable, false);
        btnRightBackgroundRes = typedArray.getResourceId(R.styleable.InputNumberView_btnRightBackground, -1);
        btnLeftBackgroundRes = typedArray.getResourceId(R.styleable.InputNumberView_btnLeftBackground, -1);
        defaultValue = typedArray.getInt(R.styleable.InputNumberView_defaultValue,0);
        this.currentNumber = defaultValue;
        Log.d("test","max ==> " + max);
        Log.d("test","min ==> " + min);
        Log.d("test","step ==> " + step);
        Log.d("test","disable ==> " + disable);
        Log.d("test","btnRightBackgroundRes ==> " + btnRightBackgroundRes);
        Log.d("test","btnLeftBackgroundRes ==> " + btnLeftBackgroundRes);
        Log.d("test","default ==> " + defaultValue);
        // 回收
        typedArray.recycle();
    }

    private void setUpEvent(){
        minusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                plusBtn.setEnabled(true);
                if(step > 0){
                    currentNumber-=step;
                    if(min != 0 && currentNumber <=  min){
                        v.setEnabled(false);
                        currentNumber = min;
                        Log.d("test","current is min value");
                        if(onNumberChangeListener != null){
                            onNumberChangeListener.onNumberMin(min);
                        }
                    }
                }
                updateText();
            }
        });

        plusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                minusBtn.setEnabled(true);
                if(step > 0){
                    currentNumber+=step;
                    if(max != 0 && currentNumber >= max){
                        v.setEnabled(false);
                        currentNumber = max;
                        Log.d("test","current is max value");
                        if(onNumberChangeListener != null){
                            onNumberChangeListener.onNumberMax(max);
                        }
                    }
                }
                updateText();
            }
        });

    }

    private void initView(Context context) {

        // 载入子view的布局文件(attachToRoot(默认为true),当为true,表示将子view绑定到父容器中)
        // 以下三种写法等价,都是把子view添加到当前容器中
        // View view = LayoutInflater.from(context).inflate(R.layout.input_number_view, this);
        // View view = LayoutInflater.from(context).inflate(R.layout.input_number_view, this, true);
        View view = LayoutInflater.from(context).inflate(R.layout.input_number_view, this, false);
        addView(view);

        // 获取各个子View对象
        minusBtn = this.findViewById(R.id.btn_minus);
        plusBtn = this.findViewById(R.id.btn_plus);
        valueEdit = this.findViewById(R.id.edit_value);

        // 初始化控件值
        updateText();
        minusBtn.setEnabled(!disable);
        //minusBtn.setBackgroundResource(btnLeftBackgroundRes);
        plusBtn.setEnabled(!disable);
        //plusBtn.setBackgroundResource(btnRightBackgroundRes);
    }


    // 获取数值函数
    public int getNumber() {
        return currentNumber;
    }

    // 设置数值函数
    public void setNumber(int value) {
        this.currentNumber = value;
        this.updateText();
    }

    // 更新数值函数
    private  void updateText(){
        valueEdit.setText(String.valueOf(currentNumber));
        if(onNumberChangeListener != null){
            onNumberChangeListener.onNumberValueChange(this.currentNumber);
        }
    }


    // 暴露接口
    // 数值更新监听接口
    public void setOnNumberChangeListener(OnNumberValueChangeListener listener){
        this.onNumberChangeListener = listener;
    }
    public interface OnNumberValueChangeListener{

        void onNumberValueChange(int value);

        void onNumberMax(int value);

        void onNumberMin(int value);
    }


    // 组合控件各项参数的get、set方法提供给用户
    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
        this.updateText();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public int getBtnRightBackgroundRes() {
        return btnRightBackgroundRes;
    }

    public void setBtnRightBackgroundRes(int btnRightBackgroundRes) {
        this.btnRightBackgroundRes = btnRightBackgroundRes;
    }

    public int getBtnLeftBackgroundRes() {
        return btnLeftBackgroundRes;
    }

    public void setBtnLeftBackgroundRes(int btnLeftBackgroundRes) {
        this.btnLeftBackgroundRes = btnLeftBackgroundRes;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
        this.currentNumber = defaultValue;
        this.updateText();
    }
}
