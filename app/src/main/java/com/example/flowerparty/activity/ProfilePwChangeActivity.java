package com.example.flowerparty.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;
import com.example.flowerparty.UserChangeNickRequest;
import com.example.flowerparty.UserChangePassRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfilePwChangeActivity extends AppCompatActivity {
    ImageView imgBack;
    EditText passNow, passNew, passNewConfirm;
    RbPreference pref;
    Button btnChange;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pw_change);

        imgBack = findViewById(R.id.img_pwc_back);
        passNow = findViewById(R.id.et_changePW_now);
        passNew = findViewById(R.id.et_changePW_new);
        passNewConfirm = findViewById(R.id.et_changePW_confirm);
        btnChange = findViewById(R.id.btn_pw_change);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 현재 비밀번호가 일치하면
        pref = new RbPreference(ProfilePwChangeActivity.this);
        String userPass = pref.getValue(RbPreference.PREF_PASS_VALUE, "default");

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nowPass = passNow.getText().toString();
                String newPass = passNew.getText().toString();
                String newCPass = passNewConfirm.getText().toString();
                if (nowPass.equals(userPass)) {
                    if (newPass.equals(newCPass)) {
                        // 비밀번호 변경 후 다시 로그인
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");

                                    if (success) {
                                        passNow.setText(null);
                                        passNew.setText(null);
                                        passNewConfirm.setText(null);
                                        passNow.clearFocus();
                                        passNew.clearFocus();
                                        passNewConfirm.clearFocus();
                                        //Toast.makeText(getApplicationContext(), "닉네임을 변경했습니다.", Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePwChangeActivity.this);
                                        builder.setTitle("비밀번호 변경 완료").setMessage("다시 로그인 하세요.");
                                        builder.setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(ProfilePwChangeActivity.this, LoginActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        pref.delValue(RbPreference.PREF_PASS_VALUE);
                                                        startActivity(intent);
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();

                                    } else {
                                        System.out.println(success);
                                        Toast.makeText(getApplicationContext(), "비밀번호 변경 실패", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("Error", "Response Error", e);
                                }
                            }
                        };
                        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
                        UserChangePassRequest userChangePassRequest = new UserChangePassRequest(userID, newPass, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ProfilePwChangeActivity.this);
                        queue.add(userChangePassRequest);
                    } else {
                        Toast.makeText(ProfilePwChangeActivity.this, "새 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfilePwChangeActivity.this, "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("userPass : ", "error : userPass - " + userPass + ", nowPass - " + nowPass);
                }
            }
        });

        // 새 비밀번호와 확인이 일치하면 update
    } /* onCreate */
}
