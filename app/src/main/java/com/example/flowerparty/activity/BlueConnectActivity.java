 package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.flowerparty.R;

 public class BlueConnectActivity extends AppCompatActivity {
     ImageView imgLArrowBlue;
     Switch switchBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_connect);

        imgLArrowBlue = findViewById(R.id.imgLArrowBlue);
        imgLArrowBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 블루투스 on, off 스위치
        switchBlue = findViewById(R.id.switchBlue);
        switchBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            LinearLayout blueOn = (LinearLayout) findViewById(R.id.blueOn);
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    blueOn.setVisibility(View.VISIBLE);
                } else {
                    blueOn.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}