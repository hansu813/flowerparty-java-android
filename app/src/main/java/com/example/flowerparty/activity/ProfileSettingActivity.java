package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flowerparty.R;

public class ProfileSettingActivity extends AppCompatActivity {
    ImageView imgClose;
    ImageView imgUNameEdit;
    ImageView imgEmailEdit;
    TextView changePW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgUNameEdit = findViewById(R.id.imgUNameEdit);
        imgUNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileCnameActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
                startActivity(intent);
            }
        });

        imgEmailEdit = findViewById(R.id.imgEmailEdit);
        imgEmailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileCEmailActivity.class);
                startActivity(intent);
            }
        });

        changePW = findViewById(R.id.ChangePW);
        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePwAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}