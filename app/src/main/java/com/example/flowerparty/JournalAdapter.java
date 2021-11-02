package com.example.flowerparty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {
    private Context context;
    private List<Journal> lists;
    private ItemClickListener itemClickListener;

    public JournalAdapter(Context context, List<Journal> lists, ItemClickListener itemClickListener) {
        this.context = context;
        this.lists = lists;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public JournalAdapter.JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_journal, parent, false);
        return new JournalViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalAdapter.JournalViewHolder holder, int position) {
        Journal journal = lists.get(position);

        holder.textTitle.setText(journal.getTitle());
        holder.textContent.setText(journal.getContent());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout linearLayout;
        public TextView textTitle, textContent;
        ItemClickListener itemClickListener;

        public JournalViewHolder(@NonNull View view, ItemClickListener itemClickListener) {
            super(view);
            linearLayout = view.findViewById(R.id.list_item);
            textTitle = view.findViewById(R.id.item_title);
            textContent = view.findViewById(R.id.item_contents);

            this.itemClickListener = itemClickListener;
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
