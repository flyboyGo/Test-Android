package com.example.jetpack.base.commonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;

import com.example.jetpack.R;

public class FirstActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private long elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        chronometer= findViewById(R.id.Chronometer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // elapsedRealtime()
        // Returns milliseconds since boot, including time spent in sleep.

        // setBase()
        // Set the time that the count-up timer is in reference to.
        // Params: base – Use the SystemClock.elapsedRealtime time base.
        chronometer.setBase(SystemClock.elapsedRealtime() - elapsedTime);
        chronometer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // getBase()
        // Return the base time as set through setBase.
        elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        chronometer.stop();
    }
}