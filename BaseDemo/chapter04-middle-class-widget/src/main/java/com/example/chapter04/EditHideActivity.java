package com.example.chapter04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.chapter04.util.ViewUtil;

public class EditHideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hide);

        EditText et_phone = findViewById(R.id.et_phone);
        EditText et_password = findViewById(R.id.et_password);

        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));

        /*
            调用编辑框对象的 addTextChangedListener 方法即可注册文本监听器。
            文本监听器的接口名称为 TextWatcher，该接口提供了3个监控方法，具体说明如下。
            beforeTextChanged：在文本改变之前触发。
            onTextChanged：在文本改变过程中触发。
            afterTextChanged：在文本改变之后触发
         */
    }

    private class  HideTextWatcher implements TextWatcher
    {
        private  EditText editText;
        private int maxLength;
        public HideTextWatcher(EditText editText, int maxLength)
        {
            this.editText = editText;
            this.maxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        // 在编辑框输入文本之后触发
        // EditText调用getText()函数后 返回Editable
        @Override
        public void afterTextChanged(Editable editable) {
            String str = editable.toString();
            if(str.length() == maxLength)
            {
                // 隐藏输入法键盘
                ViewUtil.hideOneInputMethod(EditHideActivity.this,editText);
            }
        }
    }

}