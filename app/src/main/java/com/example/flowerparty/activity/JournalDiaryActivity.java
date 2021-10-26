package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.flowerparty.R;

import java.util.Date;
import java.text.SimpleDateFormat;

import android.content.Intent;

import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class JournalDiaryActivity extends AppCompatActivity {

    TextView datetxt;
    ImageView xmark1;
    Button Button2;

    EditText et_journal_title, et_journal_contents;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_diary);





        et_journal_title  = findViewById(R.id.et_journal_title);
        et_journal_contents = findViewById(R.id.et_journal_contents);
        Button btnSave = findViewById(R.id.btn_journal_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 받아온 데이터 값 변경. 받아온 title로 조회해서 변경
                String titleStr = et_journal_title.getText().toString();
                String contentsStr = et_journal_contents.getText().toString();

                if(titleStr.length() > 0 && contentsStr.length() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("title", titleStr);
                    intent.putExtra("contents", contentsStr);
                    setResult(0, intent);

                    finish();
                }
            }
        });



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

    @Override
    protected void onPause() {
        super.onPause();
    }

}

