package com.example.livedata.base.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.livedata.R;
import com.example.livedata.base.MyFragmentViewModel;

public class TopFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载布局视图
        View root = inflater.inflate(R.layout.fragment_top, container, false);
        // 获取进度条控件对象
        SeekBar seekBar = root.findViewById(R.id.seekBar);
        // 获取自定义的ViewModel对象
        MyFragmentViewModel viewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()))
                .get(MyFragmentViewModel.class);

        // ViewModel中的数据发生改变时,通过LiveData的回调函数,实时的更新View中的数据
        viewModel.getProgress().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                seekBar.setProgress(i);
            }
        });

        // 进度条添加进度改变监听事件
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 主线程中ui线程 setValue
                viewModel.getProgress().setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return root;
    }
}