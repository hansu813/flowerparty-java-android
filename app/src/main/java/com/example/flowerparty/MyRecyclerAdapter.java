package com.example.flowerparty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    private ArrayList<PlantItem>mPlantList;

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mPlantList.get(position));
    }

    public void setPlantList(ArrayList<PlantItem>list) {
        this.mPlantList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPlantList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView listPlantName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            listPlantName = (TextView) itemView.findViewById(R.id.listPlantName);
        }

        void onBind(PlantItem item) {
            listPlantName.setText(item.getPlantName());
        }

    }
}
