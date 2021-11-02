package com.example.flowerparty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.AddPlantRequest;
import com.example.flowerparty.GetPlantRequest;
import com.example.flowerparty.RbPreference;
import com.example.flowerparty.fragment.HomeFragment;
import com.example.flowerparty.fragment.JournalFragment;
import com.example.flowerparty.fragment.MypageAFragment;
import com.example.flowerparty.fragment.PlantsFragment;
import com.example.flowerparty.R;
import com.example.flowerparty.fragment.PlantsManageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;
    Fragment plantsFragment;
    Fragment JournalFragment;
    Fragment MypageAFragment;
    RbPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        homeFragment = new HomeFragment();
        plantsFragment = new PlantsFragment();
        JournalFragment = new JournalFragment();
        MypageAFragment = new MypageAFragment();

        //초기화면 설정
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_home:
                        transaction.addToBackStack(null);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();


                        return true;

                    case R.id.tab_plants:
                        transaction.addToBackStack(null);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, plantsFragment).commit();
                        return true;

                    case R.id.tab_journal:
                        transaction.addToBackStack(null);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, JournalFragment).commit();


                        return true;

                    case R.id.tab_mypage:
                        transaction.addToBackStack(null);
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, MypageAFragment).commit();
                        return true;
                }
                return false;
            }
        });
    } /* onCreate */

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }
}