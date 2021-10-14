package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.flowerparty.R;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.flowerparty.R;
import com.example.flowerparty.activity.MainActivity;
import com.example.flowerparty.fragment.JournalFragment;

public class JournalDiaryActivity extends AppCompatActivity {

    TextView datetxt;
    ImageView xmark1;
    Button Button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_diary);

        //현재시간출력
        long systemTime = System.currentTimeMillis();
        Date d =  new Date(systemTime);
        SimpleDateFormat format1 = new SimpleDateFormat("YYYY.MM.dd");
        String formatDate = format1.format(d);

        datetxt =(TextView) findViewById(R.id.datetxt);
        datetxt.setText(formatDate);

        //activity 종료
        xmark1 = findViewById(R.id.xmark1);
        xmark1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}

