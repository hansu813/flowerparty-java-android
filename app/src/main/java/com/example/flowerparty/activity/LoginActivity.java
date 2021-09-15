package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQuere;

import com.example.flowerparty.R;

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
                Intent intent = new Intent(LoginActivity.this, RsgisterActivity.class);
                startActivity(intent);
            }
        });

        // Sign In (로그인) 버튼 클릭시 수행
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // editText 에 입력되어 있는 값 가져오기
                String userID = editTextID.getText().toString();
                String userPass = editTextPW.getText().toString();

                Response.Listener<String> reposeListener = new Response.Listener<string>() {

                }
            }
        });



        Button btnChk = findViewById(R.id.btnSignIn);

        btnChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
                startActivity(intent);
            }
        });
    }
}