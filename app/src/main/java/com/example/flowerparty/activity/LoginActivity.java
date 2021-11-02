package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.LoginRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RegisterActivity;
import com.example.flowerparty.RbPreference;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private EditText editTextID, editTextPW;
    private Button btnSignIn, btnSignUp;
    private TextView textViewFmp;
    private RbPreference pref;
    private long backBtnTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextID = findViewById(R.id.editTextID);
        editTextPW = findViewById(R.id.editTextPW);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        textViewFmp = findViewById(R.id.textViewFmp);


        // By.Jongwon : Sign up (회원가입) 버튼 클릭시 RegisterActivity로 이동
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // By. Jongwon : Sign In (로그인) 버튼 클릭시 수행
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* editText 에 입력되어 있는 값 가져오기 */
                String userID = editTextID.getText().toString();
                String userPass = editTextPW.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인 성공
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString( "userName" );
                                String userEmail = jsonObject.getString("userEmail");
                                String userImg = jsonObject.getString("imgPath");
                                //String cntPlant = jsonObject.getString("cntPlant");

                                // DB에 식물이 저장되어 있다면 바로 메인

                                //
                                Toast.makeText(getApplicationContext(), String.format("%s님 환영합니다.", userName), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                /*intent.putExtra("userID", userID);
                                intent.putExtra("userPass", userPass);
                                session = new Session(LoginActivity.this);
                                session.setUserId(userID);*/
                                pref = new RbPreference(LoginActivity.this);
                                pref.putId(RbPreference.PREF_INTRO_USER_AGREEMENT, userID);
                                pref.putEmail(RbPreference.PREF_MAIN_VALUE, userEmail);
                                pref.putName(RbPreference.PREF_SUB_VALUE, userName);
                                pref.putPass(RbPreference.PREF_PASS_VALUE, userPass);
                                startActivity(intent);
                                /*pref.put("userPass", userPass);
                                intent.putExtra("userID", userID);*/
                                finish();
                            } else { // 로그인 실패
                                Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                    } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error", "Response Error", e);
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });


        // By. Jongwon : 비밀번호 찾기 클릭 시 액티비티 이동
        textViewFmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginFmpActivity.class);
                startActivity(intent);
            }
        });




    } /* onCreate */

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            finish();
        } else {
            backBtnTime = curTime;
            Toast.makeText(LoginActivity.this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }


    }
}