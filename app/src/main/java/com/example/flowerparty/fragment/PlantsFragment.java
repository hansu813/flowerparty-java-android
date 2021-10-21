package com.example.flowerparty.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.flowerparty.R;
import com.example.flowerparty.activity.MainActivity;
import com.example.flowerparty.activity.homeSettingActivity;

public class PlantsFragment extends Fragment {
    Fragment plantsManageFragment;
    LinearLayout plantsManage;
    MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_plants, container, false);

        plantsManage = (LinearLayout) rootView.findViewById(R.id.plantsDetail);
        plantsManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlantsManageFragment.class);
                startActivity(intent);
            }
        });

        //



        return rootView;
    }
}