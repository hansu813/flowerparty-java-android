package com.example.flowerparty.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.flowerparty.R;
import com.example.flowerparty.activity.BlueActivity;
import com.example.flowerparty.activity.BlueConnectActivity;
import com.example.flowerparty.activity.PlantsNicknameActivity;
import com.example.flowerparty.activity.homeSettingActivity;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class HomeFragment extends Fragment {
    ImageButton imgBtnManage;
    ImageView imgNickname;
    ImageView imgBlueIcon;
    Switch switchWater;
    private BluetoothSPP bt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // 이미지 버튼 객체를 가져오기 위해서 변경
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

//        imgBtnManage = (ImageButton) rootview.findViewById(R.id.imgBtnHomeManage);
//        imgBtnManage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), homeSettingActivity.class);
//                startActivity(intent);
//            }
//        });

        imgNickname = (ImageView) rootview.findViewById(R.id.imgNickname);
        imgNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlantsNicknameActivity.class);
                startActivity(intent);
            }
        });

        imgBlueIcon = (ImageView) rootview.findViewById(R.id.imgBlueIcon);
        imgBlueIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BlueConnectActivity.class);
                startActivity(intent);
            }
        });

        //
        switchWater = (Switch) rootview.findViewById(R.id.switchWater);
        switchWater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    bt.send("1", true);
                } else {
                    bt.send("0", true);
                }
            }
        });


        return rootview;
    }
}