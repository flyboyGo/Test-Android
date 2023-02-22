package com.example.chapter08_fragment_max.datapass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chapter08_fragment_max.R;
import com.example.chapter08_fragment_max.fragment.FragmentBetweenA;
import com.example.chapter08_fragment_max.fragment.FragmentBetweenB;

public class FragmentDataPassBetweenActivity extends AppCompatActivity {

    private FragmentBetweenA fragmentBetweenA;
    private FragmentBetweenB fragmentBetweenB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_data_pass_between);

        fragmentBetweenA = new FragmentBetweenA();
        fragmentBetweenB = new FragmentBetweenB();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_first,fragmentBetweenA)
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_second,fragmentBetweenB)
                .commit();
    }

    public FragmentBetweenA getFragmentBetweenA()
    {
        return fragmentBetweenA;
    }

    public FragmentBetweenB getFragmentBetweenB()
    {
        return fragmentBetweenB;
    }
}