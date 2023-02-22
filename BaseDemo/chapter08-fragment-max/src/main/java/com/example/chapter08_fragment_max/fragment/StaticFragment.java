package com.example.chapter08_fragment_max.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter08_fragment_max.R;

public class StaticFragment extends Fragment {


    private TextView tv_like;
    private RatingBar rb_star;
    private RadioGroup rg_group;

    public StaticFragment() {
    }

    public static StaticFragment newInstance() {
        StaticFragment fragment = new StaticFragment();
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

        return inflater.inflate(R.layout.fragment_static, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_like = view.findViewById(R.id.tv_like);
        rb_star = view.findViewById(R.id.rb_star);
        
        rg_group = view.findViewById(R.id.rg_group);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_like)
                {
                    tv_like.setText("app喜欢");
                }
                else if(checkedId == R.id.rb_dislike)
                {
                    tv_like.setText("app不喜欢");
                }
            }
        });

        rb_star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser)
                {
                    Toast.makeText(getActivity(), "点了" + rating, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 也可以获取布局
        // getView();
    }
}