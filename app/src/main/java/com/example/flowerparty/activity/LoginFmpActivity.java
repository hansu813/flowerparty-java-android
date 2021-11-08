package com.example.flowerparty.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
//import com.example.flowerparty.GMailSender;
import com.example.flowerparty.R;
import com.example.flowerparty.SendMail;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFmpActivity extends AppCompatActivity {
    EditText editTextID;
    EditText editTextEmail;
    Button btnChangePW;
    TextView txtReturnLogin;

    SharedPreferences setting;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fmp);

        editTextID = findViewById(R.id.et_changePW_ID);
        editTextEmail = findViewById(R.id.et_changePW_Email);
        btnChangePW = findViewById(R.id.btnChangePW);
        txtReturnLogin = findViewById(R.id.txt_return_login);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        // 신청 클릭 시 입력한 아이디에 등록된 이메일이 맞다면 이메일 전송 아니면 알림 창
        btnChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = editTextID.getText().toString();
                String userEmail = editTextEmail.getText().toString();

                SendMail mailServer = new SendMail();
                mailServer.sendSecurityCode(getApplicationContext(), userEmail);


//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            boolean success = jsonObject.getBoolean("success");
//
//                            if (success) { // 서버와 아이디 이메일 일치 확인
//                                String userEmail = jsonObject.getString("userEmail");
//                                String userPW = jsonObject.getString("userPW");
//
//                                new ProcessEmailTask().execute(userEmail, null, null);
//
//                                Intent intent = new Intent(LoginFmpActivity.this, LoginFmpEmailActivity.class);
//                                startActivity(intent);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.e("Error", "Response Error", e);
//                        }
//                    }
//                };
            }
        });
    }

    private class ProcessEmailTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("전송 중입니다. 잠시만 기다려주세요.");
            dialog.show();

            super.onPreExecute();
        }



        @Override
        protected String doInBackground(String... strings) {
            String userEmail = strings[0];

//            GMailSender sender = new GMailSender("jongwonkiim@gmail.com", "");
//            try {
//                sender.sendMail(
//                        "[ Flower Party 비밀번호 ]",
//                        "\n\n안녕하세요 Flower Party 운영진입니다. \n\n요청하신 비밀번호 ["+ userEmail + "]",
//                        userEmail,
//                        setting.getString("Email", "")
//                );
//            } catch (Exception e) {
//                Log.e("SendMail", e.getMessage(), e);
//            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();
            Toast.makeText(LoginFmpActivity.this, "Email 전송되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}

