package com.example.flowerparty.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.flowerparty.R;

public class JournalFragment extends Fragment {

    ImageView chatList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_journal, container, false);

        //chatList = (ImageView) rootView.findViewById(R.id.chatList);
//        chatList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CommunityChatlistActivity.class);
//                startActivity(intent);
//            }
//        });

        return rootView;
    }
}