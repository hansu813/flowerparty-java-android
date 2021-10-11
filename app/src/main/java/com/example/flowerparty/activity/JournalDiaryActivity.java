package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flowerparty.R;
import java.util.Calendar;

public class JournalDiaryActivity extends AppCompatActivity {

    EditText datetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_diary);

        datetxt = findViewById(R.id.datetxt);
    }

    public void getDT(){
        Calendar cal = Calendar.getInstance();
        int y=0, m=0,d=0;

        y =cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH) +1;
        d = cal.get(Calendar.DAY_OF_MONTH);

        datetxt.setText((y+"."+m+"."+d));

    }


}