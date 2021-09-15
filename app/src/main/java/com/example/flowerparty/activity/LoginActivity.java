package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.LoginRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RegisterActivity2;

import org.json.JSONException;
import org.json.JSONObject;



public class LoginActivity extends AppCompatActivity {
    private EditText editTextID, editTextPW;
    private Button btnSignIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextID = findViewById(R.id.editTextID);
        editTextPW = findViewById(R.id.editTextPW);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Sign up (회원가입) 버튼 클릭시 수행
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity2.class);
                startActivity(intent);
            }
        });

        // Sign In (로그인) 버튼 클릭시 수행
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // editText 에 입력되어 있는 값 가져오기
//                String userID = editTextID.getText().toString();
//                String userPass = editTextPW.getText().toString();
//
//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            // TODO : 인코딩 문제 때문에 한글 DB 인 경우 로그인 불가
//                            System.out.println("hongchul" + response);
//                            JSONObject jsonObject = new JSONObject(response);
//                            boolean success = jsonObject.getBoolean("success");
//                            if (success) { // 로그인 성공
//                                String userID = jsonObject.getString("userID");
//                                String userPass = jsonObject.getString("userPassword");
//
//                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                intent.putExtra("userID", userID);
//                                intent.putExtra("userPass", userPass);
//                                startActivity(intent);
//                            } else { // 로그인 실패
//                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                    } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                queue.add(loginRequest);
//            }
//        });



//        Button btnChk = findViewById(R.id.btnSignIn);
//
//        btnChk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
//                startActivity(intent);
//            }
//        });
    }
}