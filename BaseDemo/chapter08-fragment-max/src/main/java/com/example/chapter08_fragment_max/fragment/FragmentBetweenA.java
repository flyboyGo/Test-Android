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

public class FragmentBetweenA extends Fragment {


    private Button button,button2;
    private TextView tv_show;

    private String data;

    public FragmentBetweenA() {

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

        return inflater.inflate(R.layout.fragment_between_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.btn_pass_data_a);
        button2 = view.findViewById(R.id.btn_pass_data_a_interface);
        tv_show = view.findViewById(R.id.tv_show);

        tv_show.setText(data);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 向Fragment B中传递数据

                Fragment fragmentB = ((FragmentDataPassBetweenActivity) getActivity()).getSupportFragmentManager().findFragmentById(R.id.fragment_container_second);
                if(fragmentB != null)
                {
                    ((FragmentBetweenB)fragmentB).setData("这是Fragment A 传递的数据");
                }
            }
        });

        // 通过接口向FragmentB传递数据
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onFragmentAChangeListener != null)
                {
                    onFragmentAChangeListener.onFragmentAChange("这是通过接口来自Fragment A的数据");
                }
            }
        });

        // 监听fragmentB的变化，接收其数据
        ((FragmentDataPassBetweenActivity) getActivity()).getFragmentBetweenB().setOnFragmentBChangeListener(new FragmentBetweenB.OnFragmentBChangeListener() {
            @Override
            public void onFragmentAChange(String data) {
                tv_show.setText(data);
            }
        });
    }

    private  OnFragmentAChangeListener onFragmentAChangeListener;

    public void setOnFragmentAChangeListener(OnFragmentAChangeListener onFragmentAChangeListener)
    {
        this.onFragmentAChangeListener = onFragmentAChangeListener;
    }

    public interface OnFragmentAChangeListener
    {
        void onFragmentAChange(String data);
    }
}