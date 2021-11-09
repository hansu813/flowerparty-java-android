package com.example.flowerparty.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
import com.example.flowerparty.JournalAdapter;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;
    Fragment plantsFragment;
    Fragment JournalFragment;
    Fragment MypageAFragment;
    Fragment PlantsManageFragment;
    RbPreference pref;
    JournalAdapter adapter;

    private long backBtnTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 프레그먼트 이동
        homeFragment = new HomeFragment();
        plantsFragment = new PlantsFragment();
        JournalFragment = new JournalFragment();
        MypageAFragment = new MypageAFragment();
        PlantsManageFragment = new PlantsManageFragment();

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

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            finish();
        } else {
            backBtnTime = curTime;
            Toast.makeText(MainActivity.this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // 다른 액티비티에서 돌아왔을 때 각 프레그먼트로 이동
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                Log.d("journal result", "requestCode");
                getSupportFragmentManager().beginTransaction().replace(R.id.container, JournalFragment).commit();
            }
            else if (resultCode == RESULT_FIRST_USER) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, plantsFragment).commit();
            }
            else if (resultCode == RESULT_CANCELED) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, MypageAFragment).commit();
            }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }
}