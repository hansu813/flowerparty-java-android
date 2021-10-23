package com.example.flowerparty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class JournalAdapter extends BaseAdapter {
    ArrayList<Journal> journalArrayList = new ArrayList<>();
    Context mContext;

    public JournalAdapter(ArrayList<Journal> journalArrayList, Context mContext) {
        this.journalArrayList = journalArrayList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return journalArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return journalArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_journal,parent,false);

        TextView tvTitle = itemView.findViewById(R.id.item_title);
        TextView tvContents = itemView.findViewById(R.id.item_contents);

        tvTitle.setText(journalArrayList.get(position).title);
        tvContents.setText(journalArrayList.get(position).contents);

        return itemView;
    }
}
