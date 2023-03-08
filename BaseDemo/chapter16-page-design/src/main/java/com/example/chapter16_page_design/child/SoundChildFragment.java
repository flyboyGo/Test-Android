package com.example.chapter16_page_design.child;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.chapter16_page_design.R;

public class SoundChildFragment extends Fragment {

    private String queryParam;
    private GridView gridView;
    private int[] images;
    private String[] text;
    private int[]color;

    public SoundChildFragment() {

    }

    public static SoundChildFragment newInstance(String param) {
        SoundChildFragment fragment = new SoundChildFragment();
        Bundle args = new Bundle();
        args.putString("queryParam", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            queryParam = getArguments().getString(queryParam);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sound_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

        initWidget(view);
    }

    private void  initData(){
        images = new int[]{R.drawable.wind,R.drawable.forest,R.drawable.sea,R.drawable.thunder,R.drawable.rain,
                           R.drawable.tree,R.drawable.bird,R.drawable.frog,R.drawable.cat};
        text = new String[]{"风声","森林","海边","雷声","雨声","树叶","鸟鸣","蛙叫","猫叫"};
        color = new int[]{R.color.first,R.color.second,R.color.third,R.color.fourth,R.color.five,
                R.color.six,R.color.seven,R.color.eight,R.color.nine};
    }

    private void initWidget(View view) {
        gridView = view.findViewById(R.id.grid_view);
        GridViewAdapter adapter = new GridViewAdapter(getContext(),images,text,color);
        gridView.setAdapter(adapter);
    }
}