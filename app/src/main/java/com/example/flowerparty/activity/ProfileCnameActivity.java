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
import com.example.flowerparty.GetPlantNickRequest;
import com.example.flowerparty.GetUserNickRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;
import com.example.flowerparty.UserChangeNickRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileCnameActivity extends AppCompatActivity {
    ImageView imgLArrow;

    TextView text_userNickname;
    EditText et_userNickname;
    Button btn_cNickname;

    private RbPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_cname);

        // 뒤로가기 버튼
        imgLArrow = findViewById(R.id.imgLArrow);
        imgLArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // userID
        pref = new RbPreference(ProfileCnameActivity.this);

        // 현재 닉네임 띄워주기
        text_userNickname = findViewById(R.id.text_userNickname);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String userNick = jsonObject.getString("userName");
                        text_userNickname.setText(userNick);
                        text_userNickname.setTextColor(Color.BLACK);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error", "Response Error", e);
                }
            }
        };
        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        GetUserNickRequest getUserNickRequest = new GetUserNickRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ProfileCnameActivity.this);
        queue.add(getUserNickRequest);

        // 새로운 닉네임 받아서 수정
        et_userNickname = findViewById(R.id.et_userNickname);
        btn_cNickname = findViewById(R.id.btn_cNickname) ;
        btn_cNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* editText 값 가져오기 */
                String nUserNick = et_userNickname.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                String cUserNick = jsonObject.getString("nUserNick");
                                text_userNickname.setText(cUserNick);
                                text_userNickname.setTextColor(Color.BLACK);
                                et_userNickname.setText(null);
                                et_userNickname.clearFocus();
                                Toast.makeText(getApplicationContext(), "닉네임을 변경했습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                System.out.println(success);
                                Toast.makeText(getApplicationContext(), "닉네임 변경 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error", "Response Error", e);
                        }
                    }
                };
                String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
                UserChangeNickRequest userChangeNickRequest = new UserChangeNickRequest(userID, nUserNick, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ProfileCnameActivity.this);
                queue.add(userChangeNickRequest);
            }
        });

    } /* onCreate */
}