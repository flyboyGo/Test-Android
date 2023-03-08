package com.example.chapter16_page_design;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private TextView tv_sound;
    private TextView tv_music;
    private SearchView searchView;
    private ImageView imageView;
    private List<Fragment> fragmentList;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initWidget();
        
        initFragment();

        initEvent();

        ViewPagerFragmentAdapter viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(),fragmentList);

        viewPager.setAdapter(viewPagerFragmentAdapter);

        viewPager.addOnPageChangeListener(this);
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        SoundFragment soundFragment = SoundFragment.newInstance();
        MusicFragment musicFragment = MusicFragment.newInstance();
        fragmentList.add(soundFragment);
        fragmentList.add(musicFragment);
    }

    private void initWidget() {
        tv_sound = this.findViewById(R.id.tv_sound);
        tv_music = this.findViewById(R.id.tv_music);
        searchView = this.findViewById(R.id.search_view);
        imageView = this.findViewById(R.id.image_view);
        viewPager = this.findViewById(R.id.view_pager);
    }

    private void initEvent(){
        tv_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                tv_sound.setBackgroundResource(R.drawable.button_sound_selected_layer_list);
                tv_music.setBackgroundResource(R.drawable.button_music_layer_list);
            }
        });

        tv_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                tv_music.setBackgroundResource(R.drawable.button_music_selected_layer_list);
                tv_sound.setBackgroundResource(R.drawable.button_sound_layer_list);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onPageSelected(int position) {
        if(position == 0){
            tv_sound.setBackgroundResource(R.drawable.button_sound_selected_layer_list);
            tv_music.setBackgroundResource(R.drawable.button_music_layer_list);
        }else if(position == 1){
            tv_music.setBackgroundResource(R.drawable.button_music_selected_layer_list);
            tv_sound.setBackgroundResource(R.drawable.button_sound_layer_list);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}