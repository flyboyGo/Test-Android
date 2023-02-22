package com.example.chapter08_fragment_extra.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chapter08_fragment_extra.R;

public class LifeCycleFragment extends Fragment {

    /*
         以下fragment是没有通过transaction放入栈中进行管理的
         1、打开界面
            onCreate() -> onCreateView() -> onActivityCreated() -> onStart() -> onResume()
         2、按下主屏键(home),当前屏呈现仍然是次fragment
            onPause() -> onStop()
         3、重新打开界面
            onStart() -> onResume()
         4、按下后退键
            onPause() -> onStop() -> onDestroyView() -> onDestroy() -> onDetach()

         以下fragment通过transaction放入栈中进行管理的
         1、打开界面
            onCreate() -> onCreateView() -> onActivityCreated() -> onStart() -> onResume()
         2、打开其他fragment,将当前fragment放入栈中进行管理的,进行了替换工作
            onPause() -> onStop() -> onDestroyView()
         3、重新打开上一个fragment
            onCreateView() -> onActivityCreated() -> onStart() -> onResume()


         Fragment生命周期注意事项
         1、将来开发者会围绕fragment生命周期花费很多时间解决问题
         2、Fragment的使用一定是需要在生命周期函数onAttach与onDetach之间
         3、Fragment的使用一定要遵循生命周期管理的规则在正确的地方写恰当的代码

     */

    public LifeCycleFragment() {

        Log.d("test","LifeCycleFragment()");
    }

    public static LifeCycleFragment newInstance() {
        LifeCycleFragment fragment = new LifeCycleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        Log.d("test","newInstance()");
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("test","onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("test","onCreateView");
        return inflater.inflate(R.layout.fragment_life_cycle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("test","onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("test","onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("test","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("test","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("test","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("test","onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("test","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("test","onDetach");
    }
}