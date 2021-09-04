package com.example.flowerparty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.flowerparty.fragment.CommunityFragment;
import com.example.flowerparty.fragment.HomeFragment;
import com.example.flowerparty.fragment.MypageFragment;
import com.example.flowerparty.fragment.PlantsFragment;
import com.example.flowerparty.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;
    Fragment plantsFragment;
    Fragment communityFragment;
    Fragment mypageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        plantsFragment = new PlantsFragment();
        communityFragment = new CommunityFragment();
        mypageFragment = new MypageFragment();

        //초기화면 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;

                    case R.id.tab_plants:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, plantsFragment).commit();
                        return true;

                    case R.id.tab_community:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, communityFragment).commit();
                        return true;

                    case R.id.tab_mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, mypageFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}