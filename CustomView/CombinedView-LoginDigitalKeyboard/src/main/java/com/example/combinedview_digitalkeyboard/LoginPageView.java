package com.example.combinedview_digitalkeyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class LoginPageView extends FrameLayout {

    private static final int SIZE_VERIFY_CODE_DEFAULT= 4;
    private int mainColor;
    private int verifyCodeSize = SIZE_VERIFY_CODE_DEFAULT;
    private OnLoginPageActionListener listener = null;
    private DigitalKeyboardView digitalKeyboardView;
    private EditText edit_verify_code;
    private EditText editTelephone;
    private TextView tv_verify_code;
    private TextView tv_login;
    private CheckBox ckConfirmProtocol;
    private boolean isRightTelephone = false;
    private boolean isConfirmAgreement = false;
    private boolean isRightVerifyCode = false;

    /**
     *  点击获取验证码 --> 手机号码是正确的
     *  点击登录 --> 正确的手机号、验证码,同意用户协议
     */
    // 手机号码的正则表达式
    private static final String REGEX_MOBILE_EXACT = "^(13[0-9]|14[014-9]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    /**
     *  倒计时多长时间:duration
     *  事件间隔: dTime
     *  通知UI更新
     */
    // 倒计时时间
    private int totalDuration;
    // 倒计时间隔
    private int dTime;
    // 剩余时间
    private int restTime = totalDuration;
    // 线程
    private Handler handler;
    // 是否正在倒计时
    private boolean isCountDowning = false;
    // 倒计时对象
    private CountDownTimer countDownTimer;


    public LoginPageView(@NonNull Context context) {
        this(context,null);
    }

    public LoginPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoginPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public LoginPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initAttrs(context,attrs);

        initView(context);

        // 禁止EditText软键盘弹出
        disableEditFocusKeypad();

        initEvent();
    }

    private void disableEditFocusKeypad() {
        editTelephone.setShowSoftInputOnFocus(false);
        edit_verify_code.setShowSoftInputOnFocus(false);
    }

    private void initEvent() {

        digitalKeyboardView.setOnDigitalKeyBoard(new DigitalKeyboardView.OnDigitalKeyBoardListener() {
            @Override
            public void onDigitalKeyBoardPress(int number) {
                EditText focusEdit = getFocusEdit();
                if(focusEdit != null){
                    Editable text = focusEdit.getText();
                    int endIndex = focusEdit.getSelectionEnd();
                    text.insert(endIndex,String.valueOf(number));
                }
            }

            @Override
            public void onDigitalKeyBoardBack() {
                EditText focusEdit = getFocusEdit();
                if(focusEdit != null){
                    int endIndex = focusEdit.getSelectionEnd();
                    Editable editText = focusEdit.getText();
                    if(endIndex > 0){
                        editText.delete(endIndex - 1,endIndex);
                    }
                }
            }
        });

        editTelephone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 手机号码变化时,检查手机格式是否符合格式要求
                Log.d("test","content --> " + s);
                String telephone = s.toString();
                boolean isMatch = telephone.matches(REGEX_MOBILE_EXACT);
                isRightTelephone = telephone.length() == 11 && isMatch;
                updateAllBtnState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_verify_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isRightVerifyCode = s.length() == 4;
                updateAllBtnState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tv_verify_code.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    // 拿到手机号码
                    String telephone = editTelephone.getText().toString().trim();
                    Log.d("test","telephone == >" + telephone);
                    listener.onGetVerifyCodeClick(telephone);
                    // 开启倒计时
                    // startCountDown();
                    beginCountDown();
                }
                else{
                    throw new IllegalArgumentException("no action to get verify code");
                }
            }
        });

        ckConfirmProtocol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isConfirmAgreement = isChecked;
                updateAllBtnState();
                if(listener != null){
                    listener.onConfirmProtocolClick(isChecked);
                }
            }
        });

        tv_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 登录
                if(listener != null){
                    // 拿到手机号码、验证码
                    listener.onLoginClick(getVerifyCode(),getTelephone());
                }
            }
        });
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_login_keyboard, this, false);
        addView(view);

        digitalKeyboardView = view.findViewById(R.id.keyboard);

        // ckConfirmProtocol = this.findViewById(R.id.ck_protocol);
        ckConfirmProtocol = view.findViewById(R.id.ck_protocol);
        if(mainColor != -1){
            ckConfirmProtocol.setTextColor(mainColor);
        }
        edit_verify_code = view.findViewById(R.id.edit_verify_code);
        if(verifyCodeSize != SIZE_VERIFY_CODE_DEFAULT){
            // edit_verify_code.setMaxEms(verifyCodeSize);
            edit_verify_code.setFilters(new InputFilter[]{new InputFilter.LengthFilter(verifyCodeSize)});
        }

        editTelephone = view.findViewById(R.id.edit_telephone);
        // 请求焦点
        editTelephone.requestFocus();

        // 禁止EditText复制、粘贴
        disableCopyAndPast(editTelephone);
        disableCopyAndPast(edit_verify_code);

        tv_verify_code = view.findViewById(R.id.tv_verify_code);
        tv_login = view.findViewById(R.id.tv_login);

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginPageView);
        mainColor = typedArray.getColor(R.styleable.LoginPageView_mainColor, -1);
        verifyCodeSize = typedArray.getInt(R.styleable.LoginPageView_verifyCodeSize, SIZE_VERIFY_CODE_DEFAULT);
        totalDuration = typedArray.getInt(R.styleable.LoginPageView_countDownDuration, 60 * 1000);
        dTime = typedArray.getInt(R.styleable.LoginPageView_countDownDTime, 1000);
        typedArray.recycle();
    }

    /**
     *  获取当前有焦点的输入框, 使用时注意要判空
     * @return
     */
    private EditText getFocusEdit(){
        View view = this.findFocus();
        if(view instanceof EditText){
            return  (EditText)view;
        }
        return null;
    }

    /**
     * 更新各个控件的状态
     * @return
     */
    private void updateAllBtnState(){
        if(!isCountDowning){
            tv_verify_code.setEnabled(isRightTelephone);
        }
        tv_login.setEnabled(isRightTelephone && isConfirmAgreement && isRightVerifyCode);
    }

    /**
     * 倒计时函数
     */
    public void startCountDown(){
        handler = App.getHandler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                restTime -= dTime;
                Log.d("test","reset == > " + restTime);
                if(restTime > 0){
                    /**
                     *  循环调用,可以形成倒计时
                     */
                    handler.postDelayed(this,dTime);
                    // 更新UI
                    tv_verify_code.setText("(" + restTime/1000 +")秒");
                    tv_verify_code.setEnabled(false);
                    isCountDowning = true;
                }
                else{
                    restTime = totalDuration;
                    isCountDowning = false;
                    tv_verify_code.setEnabled(true);
                    tv_verify_code.setText("获取验证码");
                    updateAllBtnState();
                }

            }
        });
    }

    private void beginCountDown(){
        isCountDowning = true;
        tv_verify_code.setEnabled(false);
        countDownTimer = new CountDownTimer(totalDuration,dTime){

            @Override
            public void onTick(long millisUntilFinished) {
                // 通知UI更新
                int res = (int)(millisUntilFinished / 1000);
                tv_verify_code.setText("( " + res +" )秒");
            }

            @Override
            public void onFinish() {
                // 倒计时结束
                isCountDowning = false;
                tv_verify_code.setEnabled(true);
                tv_verify_code.setText("获取验证码");
                updateAllBtnState();
                countDownTimer = null;
            }
        }.start();
    }

    /**
     *
     */
    private String getTelephone(){
        String telephone = editTelephone.getText().toString().trim();
        return telephone;
    }
    private String getVerifyCode(){
        String verifyCode = edit_verify_code.getText().toString().trim();
        return verifyCode;
    }

    /**
     * 验证码错误、成功
     */
    public void onVerifyCodeError(){
        // 清空内容
        edit_verify_code.getText().clear();
        // 停止倒计时
        if(isCountDowning && countDownTimer != null){
            isCountDowning = false;
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
    }

    /**
     * 自定义接口、暴露接口与用户产生交互
     * @param listener
     */
    public void setOnLoginPageActionListener(OnLoginPageActionListener listener){
        this.listener = listener;
    }
    public interface OnLoginPageActionListener{
        void onGetVerifyCodeClick(String phoneNum);
        void onConfirmProtocolClick(boolean isChecked);
        void onLoginClick(String verifyCode,String phoneNum);
    }



    public int getMainColor() {
        return mainColor;
    }

    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
    }

    public int getVerifyCodeSize() {
        return verifyCodeSize;
    }

    public void setVerifyCodeSize(int verifyCodeSize) {
        this.verifyCodeSize = verifyCodeSize;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getdTime() {
        return dTime;
    }

    public void setdTime(int dTime) {
        this.dTime = dTime;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void disableCopyAndPast(final EditText editText){
        try {
            if(editText == null){
                return;
            }

            editText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            editText.setLongClickable(false);
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        setInsertionDisabled(editText);
                    }
                    return false;
                }
            });

            editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("DiscouragedPrivateApi")
    private void setInsertionDisabled(EditText editText){
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(editText);

            // if this view supports insertion handles
            @SuppressLint("PrivateApi")
            Class editorClass = Class.forName("android.widget. Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject,false);
            // if this view supports selection handles
            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
