package com.example.flowerparty.fragment;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import com.example.flowerparty.R;
import com.example.flowerparty.activity.MainActivity;
import com.example.flowerparty.activity.JournalDiaryActivity;

public class JournalFragment extends Fragment {

    FloatingActionButton wbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_journal, container, false);


        wbtn = (FloatingActionButton) rootView.findViewById(R.id.wbtn);
        wbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JournalDiaryActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}