package com.example.chapter08_fragment_max.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chapter08_fragment_max.R;

public class NavFragment extends Fragment {

    private TextView tv_content;
    private String data;

    public NavFragment() {
    }

    public NavFragment(String data) {
        this.data = data;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_content = view.findViewById(R.id.tv_content);
        if(!TextUtils.isEmpty(data))
        {
            tv_content.setText(data);
        }
    }
}