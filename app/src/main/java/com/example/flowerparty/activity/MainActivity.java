package com.example.flowerparty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.flowerparty.fragment.HomeFragment;
import com.example.flowerparty.fragment.JournalFragment;
import com.example.flowerparty.fragment.MypageAFragment;
import com.example.flowerparty.fragment.PlantsFragment;
import com.example.flowerparty.R;
import com.example.flowerparty.fragment.PlantsManageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;
    Fragment plantsFragment;
    Fragment JournalFragment;
    Fragment MypageAFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        plantsFragment = new PlantsFragment();
        JournalFragment = new JournalFragment();
        MypageAFragment = new MypageAFragment();





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

                    case R.id.tab_journal:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, JournalFragment).commit();
                        return true;

                    case R.id.tab_mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, MypageAFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }


}