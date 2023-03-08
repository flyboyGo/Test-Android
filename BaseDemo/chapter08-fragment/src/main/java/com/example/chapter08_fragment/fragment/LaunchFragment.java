package com.example.chapter08_fragment.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chapter08_fragment.R;


public class LaunchFragment extends Fragment {


    public static LaunchFragment newInstance(int position, int image_id,int count) {
        LaunchFragment fragment = new LaunchFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("image_id", image_id);
        args.putInt("count",count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 上下文
        Context context = getContext();
        Bundle arguments = getArguments();
        int position = arguments.getInt("position", 0);
        int image_id = arguments.getInt("image_id", 0);
        int count = arguments.getInt("count",0);

        // 设置布局中的各个控件的内容
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_launch, container,false);
        ImageView iv_launch = view.findViewById(R.id.iv_launch);
        RadioGroup radioGroup = view.findViewById(R.id.rg_indicate);
        Button btn_start = view.findViewById(R.id.btn_start);

        iv_launch.setImageResource(image_id);

        // 每个页面都分配一组对应的单选按钮
        for (int j = 0; j < count; j++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            radioButton.setPadding(10,10,10,10);
            radioGroup.addView(radioButton);
        }

        // 当前位置的单选按钮要高亮显示，比如第二个引导页就高亮第二个单选按钮
        ((RadioButton)radioGroup.getChildAt(position)).setChecked(true);

        // 如果是最后一个引导页，则显示入口按钮，以便用户点击按钮进入主页
        if(position == count - 1)
        {
            btn_start.setVisibility(View.VISIBLE);
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "欢迎你开启美好生活", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}