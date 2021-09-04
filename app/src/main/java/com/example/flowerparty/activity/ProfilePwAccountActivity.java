package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.flowerparty.R;

public class ProfilePwAccountActivity extends AppCompatActivity {
    ImageView imgLArrowCPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pw_account);

        imgLArrowCPW = findViewById(R.id.imgLArrowCPW);
        imgLArrowCPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}