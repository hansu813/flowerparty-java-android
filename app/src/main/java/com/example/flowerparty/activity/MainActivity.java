package com.example.flowerparty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.flowerparty.fragment.JournalFragment;
import com.example.flowerparty.fragment.HomeFragment;
import com.example.flowerparty.fragment.MypageFragment;
import com.example.flowerparty.fragment.PlantsFragment;
import com.example.flowerparty.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;
    Fragment plantsFragment;
    Fragment JournalFragment;
    Fragment mypageFragment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        homeFragment = new HomeFragment();
        plantsFragment = new PlantsFragment();
        JournalFragment = new JournalFragment();
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

                    case R.id.tab_Journal:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, JournalFragment).commit();
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