package com.example.chapter16_page_design.child;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chapter16_page_design.R;

public class MusicChildFragment extends Fragment {

    private String queryParam;

    public MusicChildFragment() {
    }

    public static MusicChildFragment newInstance(String param) {
        MusicChildFragment fragment = new MusicChildFragment();
        Bundle args = new Bundle();
        args.putString("queryParam",param);
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
        return inflater.inflate(R.layout.fragment_music_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}