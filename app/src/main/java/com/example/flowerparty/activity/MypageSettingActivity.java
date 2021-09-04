package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.flowerparty.R;

public class MypageSettingActivity extends AppCompatActivity {
    ImageView imgMSettingClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_setting);

        imgMSettingClose = findViewById(R.id.imgMSettingClose);
        imgMSettingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}