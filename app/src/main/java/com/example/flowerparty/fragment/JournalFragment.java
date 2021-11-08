package com.example.flowerparty.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.flowerparty.ApiClient;
import com.example.flowerparty.AppInterface;
import com.example.flowerparty.JournalAdapter;
import com.example.flowerparty.Journal;
import com.example.flowerparty.RbPreference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import com.example.flowerparty.R;
import com.example.flowerparty.activity.JournalDiaryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JournalFragment extends Fragment {

    public static final String TAG = "JournalFragment";

    Context ct;
    FloatingActionButton wbtn;
    RecyclerView recyclerView;
    JournalAdapter adapter;
    JournalAdapter.ItemClickListener itemClickListener;

    List<Journal> list = new ArrayList<>();

    private RbPreference pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_journal, container, false);
        ct = container.getContext();

        pref = new RbPreference(ct);
        String userID = pref.getValue(RbPreference.PREF_INTRO_USER_AGREEMENT, "default");
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_journal);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ct));
        recyclerView.addItemDecoration(new DividerItemDecoration(ct, DividerItemDecoration.VERTICAL));

        selectJournal(userID);

        itemClickListener = new JournalAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int idx = list.get(position).getIdx();
                String title = list.get(position).getTitle();
                String content = list.get(position).getContent();
                Log.e(TAG, "idx : " + idx + ", title : " + title + ", content : " + content);
                Intent intent = new Intent(ct, JournalDiaryActivity.class);
                intent.putExtra("idx", idx);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                startActivity(intent);
            }
        };

        //
        wbtn = (FloatingActionButton) rootView.findViewById(R.id.wbtn);
        wbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 새로운 메모 작성
                Intent intent = new Intent(ct, JournalDiaryActivity.class);
                startActivityForResult(intent, 0);
            }
        });


        return rootView;
    }



    private void selectJournal(String userID) {
        AppInterface apiInterface = ApiClient.getClient().create(AppInterface.class);
        Call<List<Journal>> call = apiInterface.selectJournal(userID);
        call.enqueue(new Callback<List<Journal>>() {
            @Override
            public void onResponse(@NonNull Call<List<Journal>> call, @NonNull Response<List<Journal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    onGetResult(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Journal>> call, @NonNull Throwable t) {
                Log.e("selectJournal()" ," 에러 : " + t.getMessage());
            }
        });
    }

    private void onGetResult(List<Journal> lists) {
        adapter = new JournalAdapter(ct, lists, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        list = lists;
    }
}