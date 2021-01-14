package com.peridot.o_der.server;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeaAdapter extends RecyclerView.Adapter<TeaAdapter.ViewHolder> {
    ArrayList<Tea> items = new ArrayList<Tea>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.tea_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Tea item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teaname;
        TextView teaprice;

        public ViewHolder(View itemView) {
            super(itemView);

            teaname = itemView.findViewById(R.id.teaname);
            teaprice = itemView.findViewById(R.id.teaprice);
        }

        public void setItem(Tea item) {
            teaname.setText(item.getName());
            teaprice.setText(item.getPrice());
        }
    }
    public void addItem(Tea item) {
            items.add(item);
        }

        public void setItems(ArrayList<Tea> items) {
            this.items = items;
        }

        public Tea getItem(int position) {
            return items.get(position);
        }

        public void setItem(int position, Tea item) {
            items.set(position, item);
        }
}

