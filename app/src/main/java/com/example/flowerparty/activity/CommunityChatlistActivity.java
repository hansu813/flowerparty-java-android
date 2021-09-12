package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.flowerparty.R;

public class CommunityChatlistActivity extends AppCompatActivity {

    LinearLayout chatWindow;
    ImageView imgLArrowChatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_chatlist);

        chatWindow = findViewById(R.id.chatWindow);
        chatWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommunityChatActivity.class);
                startActivity(intent);
            }
        });

        imgLArrowChatList = findViewById(R.id.imgLArrowChatList);
        imgLArrowChatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}