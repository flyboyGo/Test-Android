package com.example.chapter08_fragment_max.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter08_fragment_max.R;
import com.example.chapter08_fragment_max.datapass.DataPassActivity;

public class DataPassFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView tv_data;

    public DataPassFragment() {

    }

    // 构造函数接收数据
    public DataPassFragment(String data) {
        this.mParam1 = data;
    }

    public void setParam1(String data)
    {
        this.mParam1 = data;

        if(!TextUtils.isEmpty(mParam1))
        {
            tv_data.setText(mParam1);
        }
    }

    public static DataPassFragment newInstance(String param1, String param2) {
        DataPassFragment fragment = new DataPassFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_data = view.findViewById(R.id.tv_data);

        if(!TextUtils.isEmpty(mParam1))
        {
            tv_data.setText(mParam1);
        }

        ((DataPassActivity)getActivity()).setOnDataChangeListener(new DataPassActivity.OnDataChangeListener() {
            @Override
            public void onDataChange(String data) {
                if(!TextUtils.isEmpty(data))
                {
                    tv_data.setText(data);
                }
            }
        });

        Button btn_activity = view.findViewById(R.id.btn_activity);
        btn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DataPassActivity)getActivity()).setFragmentData("这是fragment传递过来的数据");
            }
        });

        Button btn_interface = view.findViewById(R.id.btn_interface);
        btn_interface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onFragmentDataChangeListener != null)
                {
                    onFragmentDataChangeListener.onFragmentDataChange("这是fragment通过接口传递的数据");
                }
            }
        });
    }

    private OnFragmentDataChangeListener onFragmentDataChangeListener;

    public void setFragmentDataChangeListener(OnFragmentDataChangeListener onFragmentDataChangeListener)
    {
        this.onFragmentDataChangeListener = onFragmentDataChangeListener;
    }

    public interface OnFragmentDataChangeListener
    {
        void onFragmentDataChange(String data);
    }

}