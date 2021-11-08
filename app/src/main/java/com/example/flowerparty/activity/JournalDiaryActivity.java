package com.example.flowerparty.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.flowerparty.ApiClient;
import com.example.flowerparty.AppInterface;
import com.example.flowerparty.Journal;
import com.example.flowerparty.JournalAdapter;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;
import com.example.flowerparty.fragment.JournalFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Intent;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JournalDiaryActivity extends AppCompatActivity {

    TextView datetxt;
    ImageView xmark1;
    Button Button2;
    private RbPreference pref;

    EditText et_journal_title, et_journal_contents;

    public static final String TAG = "JournalDiaryActivity";

    Context ct;

    RecyclerView recyclerView;
    JournalAdapter adapter;
    JournalAdapter.ItemClickListener itemClickListener;
    List<Journal> list = new ArrayList<>();

    int idx;
    String title, content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_diary);

        pref = new RbPreference(JournalDiaryActivity.this);
        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        et_journal_title  = findViewById(R.id.et_journal_title);
        et_journal_contents = findViewById(R.id.et_journal_contents);
        Button btnSave = findViewById(R.id.btn_journal_save);
        TextView txtDelete = findViewById(R.id.text_Journal_Delete);



        // 내용 출력
        Intent intent = getIntent();
        idx = intent.getIntExtra("idx", 0);
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        Log.e(TAG, "인텐트 idx : " + idx + ", 인텐트 제목 : " + title + ", 인텐트 내용 : " + content);

        et_journal_title.setText(title);
        et_journal_contents.setText(content);

        // 저장 버튼 클릭 시
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 받아온 데이터 값 변경. 받아온 title로 조회해서 변경
                String titleStr = et_journal_title.getText().toString();
                String contentsStr = et_journal_contents.getText().toString();

                if (idx > 0) {
                    //update
                    updateJournal(idx, title, content);
                    Toast.makeText(JournalDiaryActivity.this, "수정 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(JournalDiaryActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    insertJournal(titleStr, contentsStr, userID);
                }
            }
        });

        // 삭제 텍스트 클릭 시
        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JournalDiaryActivity.this);
                builder.setTitle("Delete").setTitle("삭제하시겠습니까?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteJournal(idx, userID);
                        Toast.makeText(JournalDiaryActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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

        setResult(RESULT_OK);
    } /* onCreate */

    // Insert
    private void insertJournal(String title, String content, String userID) {
        AppInterface appInterface = ApiClient.getClient().create(AppInterface.class);
        Call<Journal> call = appInterface.insertJournal(title, content, userID);
        call.enqueue(new Callback<Journal>() {
            @Override
            public void onResponse(Call<Journal> call, Response<Journal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if (success) {
                        onSuccess(response.body().getMessage());
                    } else {
                        onError(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Journal> call, Throwable t) {
                Log.e("insertJournal()", "에러 : " + t.getMessage());
            }
        });
    }

    private void onError(String message) {
        Log.e("insertJournal()", "onResponse() 에러 : " + message);
        Toast.makeText(getApplicationContext(), "저장 실패", Toast.LENGTH_SHORT).show();
    }

    public void onSuccess(String message) {
        Log.e("insertJournal()", "onResponse() 성공 : " + message);
        setResult(RESULT_OK);
        finish();
    }

    // Update
    private void updateJournal(int idx, String title, String content) {
        AppInterface appInterface = ApiClient.getClient().create(AppInterface.class);
        title = et_journal_title.getText().toString();
        content = et_journal_contents.getText().toString();
        Call<Journal> call = appInterface.updateJournal(idx, title, content);
        call.enqueue(new Callback<Journal>() {
            @Override
            public void onResponse(Call<Journal> call, Response<Journal> response) {
                //
            }

            @Override
            public void onFailure(Call<Journal> call, Throwable t) {
                Log.e("updateJournal()", "에러 : " + t.getMessage());
            }
        });
    }

    // Delete
    private void deleteJournal(int idx, String userID) {
        AppInterface appInterface = ApiClient.getClient().create(AppInterface.class);
        Call<Journal> call = appInterface.deleteJournal(idx, userID);
        call.enqueue(new Callback<Journal>() {
            @Override
            public void onResponse(Call<Journal> call, Response<Journal> response) {
                //
            }

            @Override
            public void onFailure(Call<Journal> call, Throwable t) {
                Log.e("deleteJournal()", t.getMessage());
            }
        });
    }
}

