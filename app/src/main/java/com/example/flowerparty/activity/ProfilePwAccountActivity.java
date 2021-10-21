package com.example.flowerparty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.GetUserRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfilePwAccountActivity extends AppCompatActivity {
    ImageView imgLArrowCPW;
    TextView txt_userName, txt_userNick, txt_userEmail;
    Button btn_send;
    private RbPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pw_account);

        imgLArrowCPW = findViewById(R.id.imgLArrowCPW);
        imgLArrowCPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 프로필
        // userID
        pref = new RbPreference(ProfilePwAccountActivity.this);
        txt_userName = findViewById(R.id.txt_userID);

        String userId = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        txt_userName.setText(userId);

        // 닉네임, 이메일
        txt_userNick = findViewById(R.id.txt_userNick);
        txt_userEmail = findViewById(R.id.txt_userEmail);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String userNick = jsonObject.getString("userName");
                        String userEmail = jsonObject.getString("userEmail");

                        txt_userNick.setText(userNick);
                        txt_userNick.setTextColor(Color.WHITE);

                        txt_userEmail.setText(userEmail);
                        txt_userEmail.setTextColor(Color.WHITE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error", "Response Error", e);
                }
            }
        };
        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        GetUserRequest getUserRequest = new GetUserRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ProfilePwAccountActivity.this);
        queue.add(getUserRequest);
        //버튼 클릭 시 코드 전송
    } /* onCreate */
}