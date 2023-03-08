package com.example.chapter08_fragment.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter08_fragment.R;

public class DynamicFragment extends Fragment {

    private static final String TAG = "fragment";

    public static DynamicFragment newInstance(int position,int image_id,String desc) {
        DynamicFragment fragment = new DynamicFragment();
        // 把参数打包
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("image_id", image_id);
        args.putString("desc", desc);
        // 将参数设置到每一fragment
        fragment.setArguments(args);
        return fragment;
    }

    // 从包裹取出位置序号
    private int getPosition()
    {
        return getArguments().getInt("position",0);
    }

    @Override
    public void onAttach(@NonNull Context context) { // 把碎片贴到页面上
        super.onAttach(context);
        Log.d(TAG, "fragment onAttach position=" + getPosition());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { // 页面创建
        super.onCreate(savedInstanceState);
        Log.d(TAG, "fragment onCreate position=" + getPosition());
    }

    // 创建碎片视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 根据布局文件fragment_dynamic.xml生成视图对象
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        Bundle arguments = getArguments();
        if(arguments != null)
        {
            // 设置布局中各个控件
            ImageView iv_pic = view.findViewById(R.id.iv_pic);
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            iv_pic.setImageResource(arguments.getInt("image_id",R.drawable.huawei));
            tv_desc.setText(arguments.getString("desc"));
        }
        Log.d(TAG, "fragment onCreateView position=" + getPosition());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) { //在活动页面创建之后
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "fragment onActivityCreated position=" + getPosition());
    }

    @Override
    public void onStart() { // 页面启动
        super.onStart();
        Log.d(TAG, "fragment onStart position=" + getPosition());
    }

    @Override
    public void onResume() { // 页面恢复(fragment是可见的)
        super.onResume();
        Log.d(TAG, "fragment onResume position=" + getPosition());
    }

    @Override
    public void onPause() { // 页面暂停
        super.onPause();
        Log.d(TAG, "fragment onPause position=" + getPosition());
    }

    @Override
    public void onStop() { // 页面停止
        super.onStop();
        Log.d(TAG, "fragment onStop position=" + getPosition());
    }

    @Override
    public void onDestroyView() { // 销毁碎片视图
        super.onDestroyView();
        Log.d(TAG, "fragment onDestroyView position=" + getPosition());
    }

    @Override
    public void onDestroy() { // 页面销毁
        super.onDestroy();
        Log.d(TAG, "fragment onDestroy position=" + getPosition());
    }

    @Override
    public void onDetach() { // 把碎片从页面撕下来
        super.onDetach();
        Log.d(TAG, "fragment onDetach position=" + getPosition());
    }

}