package com.example.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlideMenuView slideMenuView = this.findViewById(R.id.slide_menu_view);
        slideMenuView.setOnActionClickListener(new SlideMenuView.OnActionClickListener() {
            @Override
            public void onReadClick() {
                Toast.makeText(getApplicationContext(),"actionRead",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTopClick() {
                Toast.makeText(getApplicationContext(),"actionTop",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick() {
                Toast.makeText(getApplicationContext(),"actionDelete",Toast.LENGTH_SHORT).show();
            }
        });

    }
}