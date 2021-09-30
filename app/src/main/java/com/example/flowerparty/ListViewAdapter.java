package com.example.flowerparty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    RadioButton radioBtnList;

    private ArrayList<ListViewItem> listview_item = new ArrayList<ListViewItem>();
    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listview_item.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // listview_item 을 inflate 하여 Convert view 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        ListViewItem listViewItem = listview_item.get(position);

        LinearLayout linearLayoutItem = (LinearLayout) convertView.findViewById(R.id.list_item);

        LinearLayout linearLayoutList = (LinearLayout) convertView.findViewById(R.id.linearLayout_list);
        linearLayoutList.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                linearLayoutItem.setBackgroundColor(R.color.gray);
            }
        });

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listview_item;
    }

    public void addItem(String no, String name) {
        ListViewItem item = new ListViewItem();

        item.setPlantNo(no);
        item.setPlantName(name);

        listview_item.add(item);
    }
}
