package com.example.flowerparty.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.flowerparty.R;
import com.example.flowerparty.activity.JournalDiaryActivity;


public class JournalFragment extends Fragment {

    Button wbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = (ViewGroup)inflater.inflate(R.layout.fragment_journal, container, false);

        wbtn = (Button)rootView.findViewById(R.id.wbtn);
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