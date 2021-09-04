package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.flowerparty.R;

public class ProfileCEmailActivity extends AppCompatActivity {
    ImageView imgLArrowCmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_c_email);

        imgLArrowCmail = findViewById(R.id.imgLArrowCmail);
        imgLArrowCmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}