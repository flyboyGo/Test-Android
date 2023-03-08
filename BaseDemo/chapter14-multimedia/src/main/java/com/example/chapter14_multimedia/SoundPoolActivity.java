package com.example.chapter14_multimedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.SoundPool;
import android.os.Bundle;

import com.example.chapter14_multimedia.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class SoundPoolActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {

    List<Sound> soundList = null;
    SoundPool soundPool = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_pool);

         RecyclerView recyclerView  = findViewById(R.id.recycle_view);
         LinearLayoutManager layoutManager = new LinearLayoutManager(this);
         layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
         recyclerView.setLayoutManager(layoutManager);

         soundPool = new SoundPool.Builder().
                 setMaxStreams(8).
                 build();

         soundList = new ArrayList<>();
         soundList.add(new Sound("first", soundPool.load(this, R.raw.first, 1)));
         soundList.add(new Sound("second", soundPool.load(this, R.raw.second, 1)));
         soundList.add(new Sound("third", soundPool.load(this, R.raw.third, 1)));
         soundList.add(new Sound("four", soundPool.load(this, R.raw.four, 1)));
         soundList.add(new Sound("five", soundPool.load(this, R.raw.five, 1)));
         soundList.add(new Sound("six", soundPool.load(this, R.raw.six, 1)));
         soundList.add(new Sound("seven", soundPool.load(this, R.raw.seven, 1)));
         soundList.add(new Sound("eight", soundPool.load(this, R.raw.eight, 1)));


         MyAdapter myAdapter = new MyAdapter(soundList, recyclerView,this);
         myAdapter.setOnItemClickListener(this);
         recyclerView.setAdapter(myAdapter);

    }

    @Override
    public void onItemClick(int position) {
        Sound sound = soundList.get(position);
        soundPool.play(sound.getSoundId(), 1, 1,1,0,1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Sound sound : soundList) {
            soundPool.unload(sound.getSoundId());
        }
        soundPool.release();
    }

    public static class Sound
    {
        String name;
        int soundId;

        public Sound() {
        }

        public Sound(String name, int soundId) {
            this.name = name;
            this.soundId = soundId;
        }

        public String getName() {
            return name;
        }

        public int getSoundId() {
            return soundId;
        }
    }
}