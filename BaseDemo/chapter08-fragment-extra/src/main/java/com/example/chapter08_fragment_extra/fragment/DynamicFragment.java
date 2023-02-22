package com.example.chapter08_fragment_extra.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chapter08_fragment_extra.R;

public class DynamicFragment extends Fragment {

    private String msg;
    private View view;

    public DynamicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取activity传递给fragment的数据
        Bundle bundle = getArguments();
        msg = bundle.getString("msg");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null)
        {
            view = inflater.inflate(R.layout.fragment_dynamic, container, false);
            TextView tv_msg = view.findViewById(R.id.tv_msg);
            tv_msg.setText(msg);
        }
        return view;
    }
}