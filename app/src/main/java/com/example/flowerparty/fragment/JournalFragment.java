package com.example.flowerparty.fragment;

import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.flowerparty.SQLiteHelper;
import com.example.flowerparty.Journal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import com.example.flowerparty.R;
import com.example.flowerparty.activity.JournalDiaryActivity;

import java.util.ArrayList;
import java.util.List;

public class JournalFragment extends Fragment {
    SQLiteHelper dbHelper;

    FloatingActionButton wbtn;

    //ArrayList<Journal> journalArrayList = new ArrayList<>();

    Context ct;

    RecyclerView rv_journal;
    RecyclerAdapter rAdapter;

    List<Journal> journalList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_journal, container, false);
        ct = container.getContext();

        dbHelper = new SQLiteHelper(ct);
        journalList = new ArrayList<>();
        journalList = dbHelper.selectAll();



        rv_journal = (RecyclerView) rootView.findViewById(R.id.rv_journal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ct);
        rv_journal.setLayoutManager(linearLayoutManager);

        rAdapter = new RecyclerAdapter(journalList);
        rv_journal.setAdapter(rAdapter);




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

        //
//        TextView journalCount = (TextView) rootView.findViewById(R.id.journalCount);
//
//
//        // DBHelper 객체 생성
//        String dbName = "Journal.db";
//        int dbVersion = 1;
//        final DBHelper dbHelper;
//
//        dbHelper = new DBHelper(ct, dbName, null, dbVersion);
//        final SQLiteDatabase db = dbHelper.getReadableDatabase();
//        final String sql = "SELECT * FROM Journal;";
//        Cursor cursor = db.rawQuery(sql, null);
//        journalCount.setText(String.valueOf(cursor.getCount())); // 일기 갯수
//        try {
//            cursor.moveToFirst();
//            if (cursor.getCount() > 0) {
//                // 데이터 베이스에 있는 값들을 넣어주는 작업
//                Journal j = new Journal(cursor.getString(1), cursor.getString(2));
//                journalArrayList.add(j);
//            }
//        } finally {
//            cursor.close();
//        }
//
//        // Dummies
//
//        // 리스트뷰 연결
//        final ListView listView = (ListView) rootView.findViewById(R.id.journal_list);
//        JournalAdapter journalAdapter = new JournalAdapter(journalArrayList, ct);
//        listView.setAdapter(journalAdapter);
//
//        // 리스트 뷰 내 아이템 클릭 시 journalDiaryActivity 로 이동
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // 이동 시 title 과 content 를 넘겨줌
//                int check_position = listView.getCheckedItemPosition();
//
//                Journal vo = (Journal)parent.getAdapter().getItem(position);
//                String t_title = vo.getTitle();
//                String c_contents = vo.getContents();
//
//                Intent intent = new Intent(ct, JournalDiaryActivity.class);
//                intent.putExtra("title", t_title);
//                intent.putExtra("contents", c_contents);
//                intent.putExtra("position", position);
//
//                startActivity(intent);
//            }
//        });
//
//        // 리스트 뷰 내 아이템 롱 클릭 시 AlertDialog 삭제
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Journal vo = (Journal) parent.getAdapter().getItem(position);
//                final String t_title = vo.getTitle();
//                String c_contents = vo.getContents();
//                AlertDialog.Builder builder = new AlertDialog.Builder(ct);
//
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // delete
//                        String sql0 = "DELETE FROM Journal WHERE title = '"+ t_title +"';";
//                        db.execSQL(sql0);
//
//                        Intent intent = new Intent(ct, getActivity().getClass());
//                        startActivity(intent);
//                    }
//                });
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.setMessage("삭제하시겠습니까?");
//                builder.setTitle("Delete Notification");
//                builder.show();
//                return true;
//            }
//        });

        // 검색 버튼 누르면

        //




        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            String strTitle = data.getStringExtra("title");
            String strContents = data.getStringExtra("contents");

            Journal journal = new Journal(strTitle, strContents, 0);
            rAdapter.addItem(journal);
            rAdapter.notifyDataSetChanged();

            dbHelper.insertJournal(journal);
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
        private List<Journal> listdata;

        public RecyclerAdapter(List<Journal> listdata) {
            this.listdata = listdata;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_journal, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Journal journal = listdata.get(position);

            holder.title.setTag(journal.getSeq());

            holder.contents.setText(journal.getContents());
            holder.title.setText(journal.getTitle());

            if(journal.getIsdone() == 0) {
                holder.img.setBackgroundColor(Color.LTGRAY);
            } else {
                holder.img.setBackgroundColor(Color.GREEN);
            }
        }

        void addItem(Journal journal) {
            listdata.add(journal);
        }

        void removeItem(int position) {
            listdata.remove(position);
        }
        class ItemViewHolder extends RecyclerView.ViewHolder {
            private TextView contents;
            private TextView title;
            private ImageView img;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.item_title);
                contents = itemView.findViewById(R.id.item_contents);
//                img = itemView.findViewById(R.id.item_image);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = getAdapterPosition();
                        int seq = (int)title.getTag();

                        if(position != RecyclerView.NO_POSITION) {
                            dbHelper.deleteJournal(seq);
                            removeItem(position);
                            notifyDataSetChanged();
                        }

                        return false;
                    }
                });
            }
        }
    }
}