package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import android.widget.RadioGroup;
import android.widget.RadioButton;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import android.widget.TimePicker;



import com.example.flowerparty.R;

public class PlantsControlDetailActivity extends AppCompatActivity {

    //시간선택
    Button timebtn1;
    Button timebtn2;
    Button button3;
    Button button4;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plants_control_detail);


        //시작시간선택1
        timebtn1 = findViewById(R.id.timebtn1);
        long systemTime = System.currentTimeMillis();
        Date d =  new Date(systemTime);
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm");
        String formatDate = format1.format(d);
        timebtn1.setText(formatDate);


        timebtn2 = findViewById(R.id.timebtn2);
        long systemTime2 = System.currentTimeMillis();
        Date d2 =  new Date(systemTime2);
        SimpleDateFormat format2 = new SimpleDateFormat("hh:mm");
        String formatDate2 = format2.format(d2);
        timebtn2.setText(formatDate2);



        //취소버튼
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void popTimePicker (View View){

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timebtn1.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();}

        public void popTimePicker2(View view){

            TimePickerDialog.OnTimeSetListener onTimeSetListener2 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;
                    timebtn1.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                }
            };

            int style2 = AlertDialog.THEME_HOLO_DARK;

            TimePickerDialog timePickerDialog2 = new TimePickerDialog(this, style2, onTimeSetListener2, hour, minute, true);
            timePickerDialog2.setTitle("Select Time");
            timePickerDialog2.show();
        }
    }
