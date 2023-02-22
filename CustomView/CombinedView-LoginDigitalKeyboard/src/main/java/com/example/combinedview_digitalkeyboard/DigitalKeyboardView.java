package com.example.combinedview_digitalkeyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class DigitalKeyboardView extends LinearLayout {

    private OnDigitalKeyBoardListener onDigitalKeyBoardListener = null;

    public DigitalKeyboardView(Context context) {
        this(context,null);
    }

    public DigitalKeyboardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DigitalKeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public DigitalKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView(context);
        initEvent();
    }

    private void initEvent() {

        for(int i = 0; i <getChildCount(); i++){
            LinearLayout linearLayout = (LinearLayout) getChildAt(i);
            for(int j = 0; j < linearLayout.getChildCount(); j++){
                LinearLayout linearLayout2 = (LinearLayout) linearLayout.getChildAt(j);
                for(int k = 0; k < linearLayout2.getChildCount(); k++){
                    linearLayout2.getChildAt(k).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(onDigitalKeyBoardListener == null){
                                Log.d("test","OnDigitalKeyBoardListener is null !");
                                return;
                            }
                            if(v.getId() == R.id.number_back){
                                onDigitalKeyBoardListener.onDigitalKeyBoardBack();
                            }
                            else{
                                CharSequence text = ((TextView) v).getText();
                                onDigitalKeyBoardListener.onDigitalKeyBoardPress(Integer.parseInt(text.toString()));
                            }
                        }
                    });
                }
            }
        }
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_digital_keyboard, this);
    }

    public interface OnDigitalKeyBoardListener{
        void onDigitalKeyBoardPress(int number);
        void onDigitalKeyBoardBack();
    }

    public void setOnDigitalKeyBoard(OnDigitalKeyBoardListener onDigitalKeyBoardListener){
        this.onDigitalKeyBoardListener = onDigitalKeyBoardListener;
    }
}
