package com.example.chapter08_fragment_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chapter08_fragment_final.fragment.DrawerFragment;
import com.google.android.material.navigation.NavigationView;

public class FragmentDrawerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_drawer);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv_drawer_menu);

        // 设置工具栏为自定义的工具栏
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        // 工具栏与drawerLayout发生联动
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // 设置默认页
        setHomePage();

        // 菜单部分添加点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        DrawerFragment drawerFragment = DrawerFragment.newInstance("首页",null);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fcv_layout, drawerFragment)
                                .commit();
                        break;
                    case R.id.menu_find:
                        DrawerFragment drawerFragment2 = DrawerFragment.newInstance("发现",null);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fcv_layout, drawerFragment2)
                                .commit();
                        break;
                    case R.id.menu_personal:
                        DrawerFragment drawerFragment3 = DrawerFragment.newInstance("我的",null);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fcv_layout, drawerFragment3)
                                .commit();
                        break;
                    default:
                        break;
                }

                if(drawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
    }

    private void setHomePage() {
        DrawerFragment drawerFragment = DrawerFragment.newInstance("首页",null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcv_layout, drawerFragment)
                .commit();

        navigationView.setCheckedItem(R.id.menu_home);
    }
}