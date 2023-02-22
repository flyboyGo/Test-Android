package com.example.navigation.base.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.navigation.R;

public class HomeFragment extends Fragment {


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 普通传参的方式,通过Bundle
//                Bundle args = new Bundle();
//                args.putString("username","Jack");

                // 传递参数的安全方式
                Bundle args = new HomeFragmentArgs.Builder()
                        .setUserName("Rose")
                        .setAge(16)
                        .build().toBundle();
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_detailFragment,args);
            }
        });
    }
}