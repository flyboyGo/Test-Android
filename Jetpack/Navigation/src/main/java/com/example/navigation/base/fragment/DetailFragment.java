package com.example.navigation.base.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.navigation.R;

public class DetailFragment extends Fragment {


    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 普通方式接收参数
//        Bundle arguments = getArguments();
        // requireArguments()内部调用了getArguments()
//        Bundle arguments = requireArguments();
//        String username = arguments.getString("username"); // 不安全,putString()与getString()的类型可能不一致
//        Log.d("test",username);

        // 接收参数的安全方式
        HomeFragmentArgs args = HomeFragmentArgs.fromBundle(getArguments());
        String userName = args.getUserName();
        int age = args.getAge();
        Log.d("test",userName + " : " + age);

        Button button = getView().findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_detailFragment_to_homeFragment);
            }
        });
    }
}