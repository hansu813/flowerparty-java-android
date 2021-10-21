package com.example.flowerparty.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.flowerparty.GetUserRequest;
import com.example.flowerparty.R;
import com.example.flowerparty.RbPreference;
import com.example.flowerparty.activity.LoginActivity;
import com.example.flowerparty.activity.PlantsChooseActivity;
import com.example.flowerparty.activity.PlantsNicknameActivity;
import com.example.flowerparty.activity.ProfileSettingActivity;
import com.example.flowerparty.activity.homeSettingActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class MypageAFragment extends Fragment {
    LinearLayout linearLayoutProfile, logout;
    ImageView imgHSetting;
    TextView textViewName;
    TextView textViewEmail;
    Context ct;
    private RbPreference pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage_a, container, false);

        linearLayoutProfile = (LinearLayout) rootView.findViewById(R.id.linearLayoutProfile);
        linearLayoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
                startActivity(intent);
            }
        });

        imgHSetting = (ImageView) rootView.findViewById(R.id.imgHSetting);
        imgHSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), homeSettingActivity.class);
                startActivity(intent);
            }
        });

        ct = container.getContext();




        // 프로필
        // userID
        pref = new RbPreference(ct);
        textViewName = (TextView) rootView.findViewById(R.id.textView_mypage_name);

        String userId = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        textViewName.setText(userId);

        // 이메일 띄우기
        textViewEmail = (TextView) rootView.findViewById(R.id.textView_mypage_email);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String userNick = jsonObject.getString("userName");
                        String userEmail = jsonObject.getString("userEmail");

                        textViewEmail.setText(userNick);
                        textViewEmail.setTextColor(Color.WHITE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error", "Response Error", e);
                }
            }
        };
        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        GetUserRequest getUserRequest = new GetUserRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ct);
        queue.add(getUserRequest);


        logout = (LinearLayout) rootView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ct)
                        .setTitle("로그아웃")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 확인시 처리 로직
                                Toast.makeText(ct, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ct, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 취소시 처리 로직
                                Toast.makeText(ct, "취소하였습니다.", Toast.LENGTH_SHORT).show();
                            }})
                        .show();
            }
        });


        return rootView;
    }
}