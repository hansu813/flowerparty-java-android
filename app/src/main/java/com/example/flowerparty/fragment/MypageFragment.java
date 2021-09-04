package com.example.flowerparty.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.flowerparty.R;
import com.example.flowerparty.activity.MypageSettingActivity;
import com.example.flowerparty.activity.ProfileSettingActivity;
import com.example.flowerparty.activity.homeSettingActivity;

public class MypageFragment extends Fragment {
    Button btnEditProfile;
    ImageView imgDot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // 이미지 버튼 객체를 가져오기 위해서 변경
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);

        btnEditProfile = (Button) rootview.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
                startActivity(intent);
            }
        });

        imgDot = (ImageView) rootview.findViewById(R.id.imgDot);
        imgDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getActivity(), MypageSettingActivity.class);
                startActivity(intent);
            }
        });


        return rootview;
    }
}