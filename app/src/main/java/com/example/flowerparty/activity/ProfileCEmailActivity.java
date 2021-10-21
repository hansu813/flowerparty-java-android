package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.GetUserRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;
import com.example.flowerparty.UserChangeEmailRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileCEmailActivity extends AppCompatActivity {
    ImageView imgLArrowCmail;

    TextView txt_email;
    EditText et_nEmail;
    Button btn_cUserEmail;

    private RbPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_c_email);

        imgLArrowCmail = findViewById(R.id.imgLArrowCmail);
        imgLArrowCmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // userID
        pref = new RbPreference(ProfileCEmailActivity.this);

        // 현재 사용자 이메일 출력
        txt_email = findViewById(R.id.txt_email);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String userNick = jsonObject.getString("userEmail");
                        txt_email.setText(userNick);
                        txt_email.setTextColor(Color.BLACK);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error", "Response Error", e);
                }
            }
        };
        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        GetUserRequest getUserRequest = new GetUserRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ProfileCEmailActivity.this);
        queue.add(getUserRequest);

        // 새로운 이메일 받아 수정
        et_nEmail = findViewById(R.id.et_nEmail);
        btn_cUserEmail = findViewById(R.id.btn_cUserEmail);
        btn_cUserEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* editText 값 가져오기 */
                String nUserEmail = et_nEmail.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                String cUserEmail = jsonObject.getString("nUserEmail");
                                txt_email.setText(cUserEmail);
                                txt_email.setTextColor(Color.BLACK);
                                et_nEmail.setText(null);
                                et_nEmail.clearFocus();
                                Toast.makeText(getApplicationContext(), "이메일을 변경했습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                System.out.println(success);
                                Toast.makeText(getApplicationContext(), "이메일 변경 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error", "Response Error", e);
                        }
                    }
                };
                String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
                UserChangeEmailRequest userChangeEmailRequest = new UserChangeEmailRequest(userID, nUserEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ProfileCEmailActivity.this);
                queue.add(userChangeEmailRequest);
            }
        });
    } /* onCreate */
}