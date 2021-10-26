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
import com.example.flowerparty.GetPlantRequest;
import com.example.flowerparty.PlantChangeNickRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;

import org.json.JSONException;
import org.json.JSONObject;

public class PlantsNicknameActivity extends AppCompatActivity {
    ImageView imgLArrow;
    TextView plantNickName;
    EditText et_plantNickName;
    Button btn_plantNickName;
    private RbPreference pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_nickname);

        // 뒤로 가기
        imgLArrow = findViewById(R.id.leftArrow_nickname);
        imgLArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //
        plantNickName = findViewById(R.id.plantNickName);
        et_plantNickName = findViewById(R.id.et_plantNickName);
        btn_plantNickName = findViewById(R.id.btn_plantNickName);
        pref = new RbPreference(PlantsNicknameActivity.this);

        // 닉네임 가져오기 Null 값이면 다른 걸로
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String plantNick = jsonObject.getString("plantNick");


                        if (plantNick.equals("null")) {
                            String dNick = "닉네임을 변경해주세요.";
                            plantNickName.setText(dNick);
                            plantNickName.setTextColor(Color.GRAY);
                        } else {
                            plantNickName.setText(plantNick);
                            plantNickName.setTextColor(Color.GRAY);
                        }
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("Error", "Response Error", e);
                }
            }
        };
        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        GetPlantRequest getPlantRequest = new GetPlantRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(PlantsNicknameActivity.this);
        queue.add(getPlantRequest);


        btn_plantNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // 수정할 닉네임
        btn_plantNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* editText 값 가져오기 */
                String plantNick = et_plantNickName.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                String plantNick = jsonObject.getString("plantNick");
                                System.out.println(plantNick);
                                //String uId = jsonObject.getString("userID");

                                plantNickName.setText(plantNick);
                                plantNickName.setTextColor(Color.BLACK);
                                et_plantNickName.setText(null);
                                et_plantNickName.clearFocus();
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
                PlantChangeNickRequest plantChangeNickRequest = new PlantChangeNickRequest(userID, plantNick, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PlantsNicknameActivity.this);
                queue.add(plantChangeNickRequest);
            }
        });



        // 버튼 클릭 시 수정

    } /* onCreate */
}