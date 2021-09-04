package com.example.flowerparty.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.flowerparty.R;
import com.example.flowerparty.activity.PlantsNicknameActivity;
import com.example.flowerparty.activity.homeSettingActivity;

public class HomeFragment extends Fragment {
    ImageButton imgBtnManage;
    ImageView imgNickname;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // 이미지 버튼 객체를 가져오기 위해서 변경
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        imgBtnManage = (ImageButton) rootview.findViewById(R.id.imgBtnHomeManage);
        imgBtnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), homeSettingActivity.class);
                startActivity(intent);
            }
        });

        imgNickname = (ImageView) rootview.findViewById(R.id.imgNickname);
        imgNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlantsNicknameActivity.class);
                startActivity(intent);
            }
        });


        return rootview;
    }
}