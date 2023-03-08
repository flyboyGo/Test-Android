package com.example.chapter08_fragment_extra.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.chapter08_fragment_extra.Interface.IFragmentCallback;
import com.example.chapter08_fragment_extra.R;

public class DynamicFragment2 extends Fragment {

    // 通信接口实例对象
    private IFragmentCallback fragmentCallback;
    private View view;

    // 给通信接口实例对象赋值
    public void setFragmentCallback(IFragmentCallback callback)
    {
        this.fragmentCallback = callback;
    }

    public DynamicFragment2() {
    }

    public static DynamicFragment2 newInstance() {
        DynamicFragment2 fragment = new DynamicFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null)
        {
            view = inflater.inflate(R.layout.fragment_dynamic_2, container, false);
            view.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentCallback.sendMessageToActivity("fragment send msg to activity");
                }
            });

            view.findViewById(R.id.btn_connect2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msgFromActivity = fragmentCallback.getMsgFromActivity(null);
                    Toast.makeText(getContext(), msgFromActivity, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
}