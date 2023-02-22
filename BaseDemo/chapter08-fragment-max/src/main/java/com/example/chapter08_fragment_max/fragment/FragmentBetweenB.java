package com.example.chapter08_fragment_max.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter08_fragment_max.R;
import com.example.chapter08_fragment_max.datapass.FragmentDataPassBetweenActivity;

public class FragmentBetweenB extends Fragment {

    private Button button,button2;
    private TextView tv_show;

    private String data;

    public FragmentBetweenB() {

    }

    public void setData(String data)
    {
        this.data = data;
        tv_show.setText(data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_between_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.btn_pass_data_b);
        button2 = view.findViewById(R.id.btn_pass_data_b_interface);
        tv_show = view.findViewById(R.id.tv_show);

        tv_show.setText(data);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 向Fragment A中传递数据

                FragmentBetweenA fragmentBetweenA = ((FragmentDataPassBetweenActivity) getActivity()).getFragmentBetweenA();
                fragmentBetweenA.setData("这是Fragment B 传递的数据");

            }
        });

        // 监听fragmentA的变化，接收其数据
        ((FragmentDataPassBetweenActivity) getActivity()).getFragmentBetweenA().setOnFragmentAChangeListener(new FragmentBetweenA.OnFragmentAChangeListener() {
            @Override
            public void onFragmentAChange(String data) {
                tv_show.setText(data);
            }
        });

        // 通过接口向FragmentA传递数据
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onFragmentBChangeListener != null)
                {
                    onFragmentBChangeListener.onFragmentAChange("这是通过接口来自Fragment B的数据");
                }
            }
        });

    }

    private OnFragmentBChangeListener onFragmentBChangeListener;

    public void setOnFragmentBChangeListener(OnFragmentBChangeListener onFragmentBChangeListener)
    {
        this.onFragmentBChangeListener = onFragmentBChangeListener;
    }

    public interface OnFragmentBChangeListener
    {
        void onFragmentAChange(String data);
    }

}